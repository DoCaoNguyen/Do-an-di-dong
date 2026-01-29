const postRepository = require('./post.repository');
const db = require('../../models');
const { Op } = require('sequelize');

class PostService {


    async getUserPosts(userId, page = 1, limit = 10) {
        const offset = (page - 1) * limit;
        const posts = await postRepository.findByUserIdForScroll(userId, limit + 1, offset);

        const hasMore = posts.length > limit;
        const data = hasMore ? posts.slice(0, limit) : posts;

        return {
            posts: data,
            hasMore: hasMore,
            nextPage: hasMore ? parseInt(page) + 1 : null
        };
    }


    async createPost(userId, postData) {
        const payload = {
            users_id: userId,
            title: postData.title,
            content: postData.content,
            image_url: postData.image_url || null,
            status: 'public'
        };
        const newPost = await postRepository.create(payload);

        let tagInput = postData.tag || postData.tags;

        if (tagInput) {
            let tagNames = [];

            if (typeof tagInput === 'string') {
                tagNames = tagInput.split(',').map(name => name.trim());
            } else if (Array.isArray(tagInput)) {
                tagNames = tagInput.map(name => String(name).trim());
            }


            tagNames = tagNames.filter(name => name.length > 0);

            const tagObjects = await Promise.all(
                tagNames.map(async (name) => {
                    const [tag] = await db.Tag.findOrCreate({
                        where: { tags_name: name },
                        defaults: { tags_name: name }
                    });
                    return tag;
                })
            );
            if (tagObjects.length > 0) {
                await newPost.setTags(tagObjects);
            }
        }

        return await db.Post.findByPk(newPost.id, {
            include: [{
                model: db.Tag,
                as: 'tags',
                attributes: ['id', 'tags_name'],
                through: { attributes: [] }
            }]
        });
    }

    async getNewsfeed(currentUser, query) {
        const page = parseInt(query.page) || 1;
        const limit = parseInt(query.limit) || 10;
        const offset = (page - 1) * limit;

        const interests = (currentUser && currentUser.interests) ? currentUser.interests : [];

        const result = await postRepository.findAllForNewsfeed({
            limit,
            offset,
            interests,
            sortBy: query.sortBy || 'id',
            order: query.order || 'DESC'
        });

        const hasMore = offset + result.rows.length < result.count;

        return {
            posts: result.rows,
            totalItems: result.count,
            currentPage: page,
            hasMore: hasMore,
            nextPage: hasMore ? page + 1 : null
        };
    }



    async updatePost(postId, userId, updateData, file) {
        const post = await db.Post.findByPk(postId);
        if (!post) throw new Error('Bài viết không tồn tại');

        if (post.users_id !== userId) {
            throw new Error('Bạn không có quyền chỉnh sửa bài viết này');
        }

        const newImageUrl = file ? file.path : (updateData.image_url || post.image_url);

        await post.update({
            title: updateData.title || post.title,
            content: updateData.content || post.content,
            image_url: newImageUrl,
        });

        let tagInput = updateData.tag || updateData.tags;
        if (tagInput !== undefined && tagInput !== null) {
            if (tagInput === '') {
                await post.setTags([]);
            } else {
                let tagNames = [];
                if (typeof tagInput === 'string') {
                    tagNames = tagInput.split(',').map(name => name.trim());
                } else if (Array.isArray(tagInput)) {
                    tagNames = tagInput.map(name => String(name).trim());
                }
                tagNames = tagNames.filter(name => name.length > 0);

                if (tagNames.length > 0) {
                    const tagObjects = await Promise.all(
                        tagNames.map(async (name) => {
                            const [tag] = await db.Tag.findOrCreate({
                                where: { tags_name: name },
                                defaults: { tags_name: name }
                            });
                            return tag;
                        })
                    );
                    await post.setTags(tagObjects);
                }
            }
        }

        return await db.Post.findByPk(postId, {
            include: [{
                model: db.Tag,
                as: 'tags',
                attributes: ['id', 'tags_name'],
                through: { attributes: [] }
            }]
        });
    }


    async sharePost(userId, postId) {

        const post = await db.Post.findByPk(postId);
        if (!post) {
            throw new Error('Bài viết không tồn tại');
        }

        // Lưu thông tin người share và bài được share vào bảng trung gian
        return await db.SharePost.create({
            users_id: userId,
            posts_id: postId
        });
    }

    async getPostsByTagName(tagName) {
        return await db.Post.findAll({
            attributes: ['id', 'title', 'content', 'image_url'],
            include: [
                {
                    model: db.Tag,
                    as: 'tags',
                    attributes: ['id', 'tags_name'],
                    through: { attributes: [] }
                },
                {
                    model: db.User,
                    as: 'user',
                    attributes: ['id', 'username', 'avatarurl']
                }
            ],
            where: {
                id: {
                    [db.Sequelize.Op.in]: db.sequelize.literal(`(
                    SELECT posts_id FROM PostTags 
                    JOIN Tags ON PostTags.tags_id = Tags.id 
                    WHERE Tags.tags_name LIKE '%${tagName}%'
                )`)
                }
            },
            limit: 10
        });
    }

    async getPostsByTagId(tagId, page = 1, limit = 10) {
        const p = parseInt(page) || 1;
        const l = parseInt(limit) || 10;
        const offset = (p - 1) * l;

        const result = await postRepository.findByTagIdForScroll(tagId, l, offset);
        const hasMore = offset + result.rows.length < result.count;

        return {
            posts: result.rows,
            totalItems: result.count,
            currentPage: p,
            hasMore: hasMore,
            nextPage: hasMore ? p + 1 : null
        };
    }


}

module.exports = new PostService();