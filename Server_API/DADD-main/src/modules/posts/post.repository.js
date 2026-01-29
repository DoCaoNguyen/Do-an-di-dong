const { Post, User, Tag, db } = require('../../models'); 
const { Op } = require('sequelize');

class PostRepository {

    get commonInclude() {
        return [
            {
                model: User,
                as: 'user',
                attributes: ['id', 'username', 'avatarurl']
            },
            {
                model: Tag,
                as: 'tags', 
                attributes: ['id', 'tags_name'],
                through: { attributes: [] }
            }
        ];
    }

    async findById(id) {
        return await Post.findByPk(id, {
            include: this.commonInclude
        });
    }

    async create(postData) {
        return await Post.create(postData);
    }

    async update(id, updateData) {
        await Post.update(updateData, {
            where: { id }
        });
        return await this.findById(id); 
    }

    async findAllForNewsfeed({ limit, offset, interests = [], sortBy = 'id', order = 'DESC' }) {
        let whereCondition = { status: 'public' };
        
        if (interests && interests.length > 0) {
            whereCondition[Op.or] = interests.map(tag => ({
                [Op.or]: [
                    { title: { [Op.like]: `%${tag}%` } },
                    { content: { [Op.like]: `%${tag}%` } }
                ]
            }));
        }

        return await Post.findAndCountAll({
            where: whereCondition,
            limit: parseInt(limit),
            offset: parseInt(offset),
            order: [[sortBy, order]],
            include: this.commonInclude,
            distinct: true
        });
    }

    async findByUserIdForScroll(users_id, limit, offset) {
        return await Post.findAll({
            where: { users_id },
            limit: parseInt(limit),
            offset: parseInt(offset),
            order: [['id', 'DESC']],
            include: this.commonInclude
        });
    }

    async findByTagIdForScroll(tagId, limit, offset) {
        return await Post.findAndCountAll({
            limit: parseInt(limit),
            offset: parseInt(offset),
            order: [['id', 'DESC']],
            include: [
                {
                    model: Tag,
                    as: 'tags',
                    where: { id: tagId }, 
                    through: { attributes: [] }
                },
                {
                    model: User,
                    as: 'user',
                    attributes: ['id', 'username', 'avatarurl']
                }
            ],
            distinct: true 
        });
    }

    async incrementShareCount(postId) {
        return await Post.increment('share_count', {
            where: { id: postId }
        });
    }

    async createShare(userId, postId) {
      
        const { SharePost } = require('../../models');
        return await SharePost.create({
            users_id: userId,
            posts_id: postId
        });
    }
}

module.exports = new PostRepository();