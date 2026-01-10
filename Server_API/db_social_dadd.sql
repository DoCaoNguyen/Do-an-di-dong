-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 10, 2026 lúc 11:18 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `forum_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin_actions`
--

CREATE TABLE `admin_actions` (
  `id` int(11) NOT NULL,
  `actions_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `admin_actions`
--

INSERT INTO `admin_actions` (`id`, `actions_name`) VALUES
(8, 'Add Tag'),
(2, 'Ban User'),
(9, 'Broadcast'),
(4, 'Change Role'),
(6, 'Clear Cache'),
(1, 'Delete Post'),
(10, 'Export Data'),
(7, 'Fix Bug'),
(5, 'Review Report'),
(3, 'Update System');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin_logs`
--

CREATE TABLE `admin_logs` (
  `id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `admin_actions_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `admin_logs`
--

INSERT INTO `admin_logs` (`id`, `users_id`, `admin_actions_id`, `created_at`) VALUES
(1, 1, 1, '2026-01-06 14:39:25'),
(2, 1, 3, '2026-01-06 14:39:25'),
(3, 1, 8, '2026-01-06 14:39:25'),
(4, 1, 9, '2026-01-06 14:39:25'),
(5, 1, 2, '2026-01-06 14:39:25'),
(6, 1, 4, '2026-01-06 14:39:25'),
(7, 1, 5, '2026-01-06 14:39:25'),
(8, 1, 6, '2026-01-06 14:39:25'),
(9, 1, 7, '2026-01-06 14:39:25'),
(10, 1, 10, '2026-01-06 14:39:25');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `posts_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `root` int(11) DEFAULT NULL,
  `comment` text NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `delete_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `comments`
--

INSERT INTO `comments` (`id`, `posts_id`, `users_id`, `parent`, `root`, `comment`, `image_url`, `created_at`, `updated_at`, `delete_at`) VALUES
(1, 1, 2, NULL, NULL, 'Tuyệt vời quá admin ơi!', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(2, 1, 3, NULL, NULL, 'Chúc mừng hệ thống ra mắt.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(3, 2, 1, NULL, NULL, 'Mình cũng vừa đi về, công nhận đẹp thật.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(4, 3, 4, NULL, NULL, 'Cảm ơn công thức của bạn nhé.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(5, 4, 5, NULL, NULL, 'AI đúng là đáng sợ nhưng cũng rất hữu ích.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(6, 5, 6, NULL, NULL, 'Tiếc cho đội thua cuộc quá.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(7, 6, 7, NULL, NULL, 'Nhạc hay lắm bạn.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(8, 7, 8, NULL, NULL, 'Mình thích phong cách này.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(9, 8, 9, NULL, NULL, 'Thông tin rất hữu ích.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL),
(10, 9, 10, NULL, NULL, 'Bài viết rất tâm huyết.', NULL, '2026-01-06 14:39:25', '2026-01-06 14:39:25', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `follows`
--

CREATE TABLE `follows` (
  `id` int(11) NOT NULL,
  `following_id` int(11) NOT NULL,
  `follower_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `follows`
--

INSERT INTO `follows` (`id`, `following_id`, `follower_id`, `created_at`, `deleted_at`) VALUES
(1, 1, 2, '2026-01-06 14:39:25', NULL),
(2, 1, 3, '2026-01-06 14:39:25', NULL),
(3, 2, 1, '2026-01-06 14:39:25', NULL),
(4, 3, 1, '2026-01-06 14:39:25', NULL),
(5, 4, 5, '2026-01-06 14:39:25', NULL),
(6, 5, 4, '2026-01-06 14:39:25', NULL),
(7, 6, 7, '2026-01-06 14:39:25', NULL),
(8, 7, 8, '2026-01-06 14:39:25', NULL),
(9, 8, 9, '2026-01-06 14:39:25', NULL),
(10, 9, 10, '2026-01-06 14:39:25', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `logins`
--

CREATE TABLE `logins` (
  `id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `auth_provider` varchar(50) DEFAULT 'local',
  `failed_login_count` int(11) DEFAULT 0,
  `lockout_until` timestamp NULL DEFAULT NULL,
  `login_count` int(11) DEFAULT 0,
  `is_first_login` tinyint(1) DEFAULT 1,
  `create_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_login_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `logins`
--

INSERT INTO `logins` (`id`, `users_id`, `password_hash`, `auth_provider`, `failed_login_count`, `lockout_until`, `login_count`, `is_first_login`, `create_at`, `last_login_at`) VALUES
(1, 1, '$2b$10$OKvpFWnEaqJZublfl6y0OeEQrLIeZvpcxEDc9YKP5f0LAqhDHjCC2', 'local', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(2, 2, '$2b$10$E8Ig1RFUIwqSqA/4BPrkwOegBlBl9yQlqjdl1vQmplsSSK3GbTuMO', 'local', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(3, 3, '$2b$10$BlJr8s61xzM9ZEKV4ozT9.IA6/DT/g3VGwxYuIknAo/EpjkgeEnlW', 'google', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(4, 4, '$2b$10$x3UTPwLVmT7h78Ihg2Jn.e2BgIvddbFRYk60bD6LRqQxDGE9X2Ryy', 'local', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(5, 5, '$2b$10$KSSp5DelglomNh0k2gG63.vLQfqfbVR4qgB0qA/JlMZOpKyH7opgi', 'facebook', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(6, 6, '$2b$10$XgOvbmvotuyXqGju6k.8.O7MkM6Tsx7g42hgWVg8xRHt8P.KJa21C', 'local', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(7, 7, '$2b$10$yYqPTN4XXScHedVKKcENZuKirW2yojKvYahwhsOs/iJwCNH6SmR0S', 'local', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(8, 8, '$2b$10$ujG20MwFfg0WuQmzoPYtp.1IbrGEo/mTUtiA6pVQ9Fj/JcQzAOLg2', 'google', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(9, 9, '$2b$10$7hda/lIc40HTQvlTwLvvauL9v.4X6N3JwO4QvHdayCjOdxRm5ROjG', 'local', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(10, 10, '$2b$10$LWjtLlzBx2YglsAn05J.dOVBpsbG7lEndMT2Rinbp.Aw.OSfwL4eW', 'local', 0, NULL, 0, 1, '2026-01-06 14:39:25', NULL),
(11, 13, '$2b$10$YIGLQbECjoe8F7GiTySyVO/5OwvgJZSyZic7fhC1lSbpIGemh/RdG', 'local', 0, NULL, 0, 1, '2026-01-07 12:44:16', NULL),
(12, 14, '$2b$10$bw8fQOIhoBu3a8BaYDqyQ.5PhZoA0fUgfXfd2j4gZHEIavn/bGZeS', 'local', 0, NULL, 0, 1, '2026-01-07 14:35:53', NULL),
(13, 15, '$2b$10$xeRcBLgsS36z1YG3mF3Bb.oTqZH88vNNZIekA/3IAqW.Hc4XD2EI2', 'local', 0, NULL, 0, 1, '2026-01-07 14:59:31', NULL),
(14, 16, '$2b$10$fzFYtMLQmtW6.NOLBxhGxepFbwi5v5wii8CG5b304EFe7/X9MxDMa', 'local', 0, NULL, 0, 1, '2026-01-08 07:18:19', NULL),
(15, 17, '$2b$10$lXDMkoeXFUjmGCG4Op60cOHa7aHX0ynbTAtr3/zzwfBx2yfzE0rbS', 'local', 0, NULL, 0, 1, '2026-01-08 12:01:10', NULL),
(16, 18, '$2b$10$Ae1daQbCdcILPQSTF9CgUOp8g6b5VRa61/XaGUY5voW5hmkkBRrPy', 'local', 0, NULL, 0, 1, '2026-01-10 10:10:46', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `is_seen` tinyint(1) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `notifications`
--

INSERT INTO `notifications` (`id`, `users_id`, `title`, `content`, `is_seen`, `created_at`) VALUES
(1, 1, 'Thông báo mới', 'Bạn có một follower mới.', 0, '2026-01-06 14:39:25'),
(2, 2, 'Lượt thích mới', 'Admin đã thích bài viết của bạn.', 0, '2026-01-06 14:39:25'),
(3, 3, 'Bình luận mới', 'Có người vừa bình luận vào bài viết của bạn.', 0, '2026-01-06 14:39:25'),
(4, 1, 'Báo cáo hệ thống', 'Hệ thống sẽ bảo trì vào tối nay.', 0, '2026-01-06 14:39:25'),
(5, 4, 'Nhắc nhở', 'Đừng quên cập nhật ảnh đại diện nhé.', 0, '2026-01-06 14:39:25'),
(6, 5, 'Follower mới', 'Hoàng E đã bắt đầu theo dõi bạn.', 0, '2026-01-06 14:39:25'),
(7, 6, 'Thông báo', 'Bài viết của bạn đã được duyệt.', 0, '2026-01-06 14:39:25'),
(8, 7, 'Tin nhắn', 'Bạn nhận được lời mời tham gia nhóm.', 0, '2026-01-06 14:39:25'),
(9, 8, 'Cảnh báo', 'Phát hiện đăng nhập lạ.', 0, '2026-01-06 14:39:25'),
(10, 9, 'Khuyến mãi', 'Tặng bạn mã giảm giá cho gói VIP.', 0, '2026-01-06 14:39:25'),
(11, 2, 'Người theo dõi mới', 'Ai đó đã bắt đầu theo dõi bạn', 0, '2026-01-08 14:14:49');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `posts`
--

CREATE TABLE `posts` (
  `id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(20) DEFAULT 'public'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `posts`
--

INSERT INTO `posts` (`id`, `users_id`, `title`, `content`, `image_url`, `created_at`, `status`) VALUES
(1, 1, 'Chào mừng đến với mạng xã hội', 'Đây là bài viết đầu tiên trên hệ thống.', NULL, '2026-01-06 14:39:25', 'public'),
(2, 2, 'Review chuyến đi Đà Lạt', 'Đà Lạt mùa này rất đẹp và thơ mộng...', NULL, '2026-01-06 14:39:25', 'public'),
(3, 3, 'Công thức nấu Phở', 'Bí quyết để có nồi nước dùng trong và ngọt...', NULL, '2026-01-06 14:39:25', 'public'),
(4, 4, 'Tương lai của AI', 'Trí tuệ nhân tạo đang thay đổi thế giới như thế nào?', NULL, '2026-01-06 14:39:25', 'public'),
(5, 5, 'Kết quả trận bóng đêm qua', 'Một trận đấu đầy kịch tính giữa hai đội...', NULL, '2026-01-06 14:39:25', 'public'),
(6, 6, 'Playlist nhạc chill cuối tuần', 'Chia sẻ cùng mọi người những bản nhạc hay...', NULL, '2026-01-06 14:39:25', 'public'),
(7, 7, 'Xu hướng thời trang 2026', 'Năm nay màu xanh lá sẽ lên ngôi...', NULL, '2026-01-06 14:39:25', 'public'),
(8, 8, 'Cách giữ sức khỏe mùa dịch', 'Ăn uống đủ chất và tập thể dục đều đặn...', NULL, '2026-01-06 14:39:25', 'public'),
(9, 9, 'Khởi nghiệp cần gì?', 'Những bài học đắt giá cho người mới bắt đầu...', NULL, '2026-01-06 14:39:25', 'public'),
(10, 10, 'Top 5 phim hay tháng này', 'Đừng bỏ lỡ những siêu phẩm điện ảnh sau...', NULL, '2026-01-06 14:39:25', 'public'),
(11, 14, '', 'Đây là bài viết đầu tiên của tôi trên Forum!', NULL, '2026-01-07 15:27:39', 'public'),
(12, 14, '', 'Đây là bài viết đầu tiên của tôi trên Forum!', NULL, '2026-01-07 15:29:07', 'public');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `posts_tags`
--

CREATE TABLE `posts_tags` (
  `id` int(11) NOT NULL,
  `posts_id` int(11) NOT NULL,
  `tags_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `posts_tags`
--

INSERT INTO `posts_tags` (`id`, `posts_id`, `tags_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 1),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 9),
(9, 9, 10),
(10, 10, 8);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `share_posts`
--

CREATE TABLE `share_posts` (
  `id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `posts_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `share_posts`
--

INSERT INTO `share_posts` (`id`, `users_id`, `posts_id`, `created_at`) VALUES
(1, 2, 1, '2026-01-06 14:39:25'),
(2, 3, 1, '2026-01-06 14:39:25'),
(3, 1, 2, '2026-01-06 14:39:25'),
(4, 5, 4, '2026-01-06 14:39:25'),
(5, 6, 5, '2026-01-06 14:39:25'),
(6, 7, 6, '2026-01-06 14:39:25'),
(7, 8, 7, '2026-01-06 14:39:25'),
(8, 9, 8, '2026-01-06 14:39:25'),
(9, 10, 9, '2026-01-06 14:39:25'),
(10, 4, 3, '2026-01-06 14:39:25');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tags`
--

CREATE TABLE `tags` (
  `id` int(11) NOT NULL,
  `tags_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tags`
--

INSERT INTO `tags` (`id`, `tags_name`) VALUES
(6, 'AmNhac'),
(3, 'AmThuc'),
(1, 'CongNghe'),
(2, 'Dulich'),
(4, 'GiaoDuc'),
(10, 'KinhDoanh'),
(8, 'PhimAnh'),
(9, 'SucKhoe'),
(5, 'TheThao'),
(7, 'ThoiTrang');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(20) DEFAULT 'user',
  `interests` text DEFAULT NULL,
  `avatarurl` varchar(255) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `status` varchar(20) DEFAULT 'active',
  `reset_otp` varchar(6) DEFAULT NULL,
  `reset_otp_expires_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `role`, `interests`, `avatarurl`, `gender`, `phone`, `birthday`, `status`, `reset_otp`, `reset_otp_expires_at`) VALUES
(1, 'nguyenvana', 'vana@example.com', 'admin', NULL, NULL, 'Male', NULL, NULL, 'active', NULL, NULL),
(2, 'tranbibi', 'bibi@example.com', 'user', NULL, NULL, 'Female', NULL, NULL, 'active', NULL, NULL),
(3, 'lequangc', 'quangc@example.com', 'user', NULL, NULL, 'Male', NULL, NULL, 'active', NULL, NULL),
(4, 'phamthid', 'thid@example.com', 'user', NULL, NULL, 'Female', NULL, NULL, 'active', NULL, NULL),
(5, 'hoange', 'hoange@example.com', 'user', NULL, NULL, 'Male', NULL, NULL, 'active', NULL, NULL),
(6, 'vuthif', 'thif@example.com', 'user', NULL, NULL, 'Female', NULL, NULL, 'active', NULL, NULL),
(7, 'dangvangg', 'vangg@example.com', 'user', NULL, NULL, 'Male', NULL, NULL, 'active', NULL, NULL),
(8, 'buithih', 'thih@example.com', 'user', NULL, NULL, 'Female', NULL, NULL, 'active', NULL, NULL),
(9, 'ngoandi', 'ngoandi@example.com', 'user', NULL, NULL, 'Male', NULL, NULL, 'active', NULL, NULL),
(10, 'lythik', 'lythik@example.com', 'user', NULL, NULL, 'Female', NULL, NULL, 'active', NULL, NULL),
(11, 'testuser', 'testuser@gmail.com', 'User', NULL, NULL, NULL, NULL, NULL, 'active', NULL, NULL),
(12, 'test1', 'test1@gmail.com', 'User', NULL, NULL, NULL, NULL, NULL, 'active', NULL, NULL),
(13, 'test2', 'test2@gmail.com', 'user', NULL, NULL, NULL, NULL, NULL, 'active', NULL, NULL),
(14, 'test3', 'test3@gmail.com', 'user', NULL, NULL, NULL, NULL, NULL, 'active', NULL, NULL),
(15, 'phuong', 'test4@gmail.com', 'user', NULL, NULL, 'Nam', '0987654321', NULL, 'active', NULL, NULL),
(16, 'testupdate', 'test5@gmail.com', 'user', NULL, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/', 'Nu', '01234567897', '2005-11-21', 'active', NULL, NULL),
(17, 'testupdate1', 'test6@gmail.com', 'user', NULL, '', 'Nu', '01234567897', '2005-11-21', 'active', NULL, NULL),
(18, 'test7', 'test7@gmail.com', 'user', NULL, NULL, NULL, NULL, NULL, 'active', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_interests`
--

CREATE TABLE `user_interests` (
  `id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user_interests`
--

INSERT INTO `user_interests` (`id`, `users_id`, `tag_id`) VALUES
(1, 1, 1),
(2, 1, 10),
(3, 2, 2),
(4, 3, 3),
(5, 4, 1),
(6, 5, 5),
(7, 6, 6),
(8, 7, 7),
(9, 8, 9),
(10, 9, 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `votes`
--

CREATE TABLE `votes` (
  `id` int(11) NOT NULL,
  `posts_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `votetype` tinyint(4) DEFAULT NULL COMMENT '1: Upvote, -1: Downvote'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `votes`
--

INSERT INTO `votes` (`id`, `posts_id`, `users_id`, `votetype`) VALUES
(1, 1, 2, 1),
(2, 1, 3, 1),
(3, 2, 1, 1),
(4, 3, 5, 1),
(5, 4, 6, 1),
(6, 5, 7, -1),
(7, 6, 8, 1),
(8, 7, 9, 1),
(9, 8, 10, 1),
(10, 9, 2, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admin_actions`
--
ALTER TABLE `admin_actions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `actions_name` (`actions_name`);

--
-- Chỉ mục cho bảng `admin_logs`
--
ALTER TABLE `admin_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `users_id` (`users_id`),
  ADD KEY `admin_actions_id` (`admin_actions_id`);

--
-- Chỉ mục cho bảng `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `posts_id` (`posts_id`),
  ADD KEY `users_id` (`users_id`),
  ADD KEY `parent` (`parent`),
  ADD KEY `root` (`root`);

--
-- Chỉ mục cho bảng `follows`
--
ALTER TABLE `follows`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `following_id` (`following_id`,`follower_id`),
  ADD KEY `follower_id` (`follower_id`);

--
-- Chỉ mục cho bảng `logins`
--
ALTER TABLE `logins`
  ADD PRIMARY KEY (`id`),
  ADD KEY `users_id` (`users_id`);

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `users_id` (`users_id`);

--
-- Chỉ mục cho bảng `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `users_id` (`users_id`);

--
-- Chỉ mục cho bảng `posts_tags`
--
ALTER TABLE `posts_tags`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `posts_id` (`posts_id`,`tags_id`),
  ADD KEY `tags_id` (`tags_id`);

--
-- Chỉ mục cho bảng `share_posts`
--
ALTER TABLE `share_posts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `users_id` (`users_id`),
  ADD KEY `posts_id` (`posts_id`);

--
-- Chỉ mục cho bảng `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tags_name` (`tags_name`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Chỉ mục cho bảng `user_interests`
--
ALTER TABLE `user_interests`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_id` (`users_id`,`tag_id`),
  ADD KEY `tag_id` (`tag_id`);

--
-- Chỉ mục cho bảng `votes`
--
ALTER TABLE `votes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `posts_id` (`posts_id`,`users_id`),
  ADD KEY `users_id` (`users_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `admin_actions`
--
ALTER TABLE `admin_actions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `admin_logs`
--
ALTER TABLE `admin_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `follows`
--
ALTER TABLE `follows`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `logins`
--
ALTER TABLE `logins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `posts_tags`
--
ALTER TABLE `posts_tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `share_posts`
--
ALTER TABLE `share_posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `tags`
--
ALTER TABLE `tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `user_interests`
--
ALTER TABLE `user_interests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `votes`
--
ALTER TABLE `votes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `admin_logs`
--
ALTER TABLE `admin_logs`
  ADD CONSTRAINT `admin_logs_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `admin_logs_ibfk_2` FOREIGN KEY (`admin_actions_id`) REFERENCES `admin_actions` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`parent`) REFERENCES `comments` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `comments_ibfk_4` FOREIGN KEY (`root`) REFERENCES `comments` (`id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `follows`
--
ALTER TABLE `follows`
  ADD CONSTRAINT `follows_ibfk_1` FOREIGN KEY (`following_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `follows_ibfk_2` FOREIGN KEY (`follower_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `logins`
--
ALTER TABLE `logins`
  ADD CONSTRAINT `logins_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `posts_tags`
--
ALTER TABLE `posts_tags`
  ADD CONSTRAINT `posts_tags_ibfk_1` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `posts_tags_ibfk_2` FOREIGN KEY (`tags_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `share_posts`
--
ALTER TABLE `share_posts`
  ADD CONSTRAINT `share_posts_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `share_posts_ibfk_2` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `user_interests`
--
ALTER TABLE `user_interests`
  ADD CONSTRAINT `user_interests_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_interests_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `votes`
--
ALTER TABLE `votes`
  ADD CONSTRAINT `votes_ibfk_1` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `votes_ibfk_2` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
