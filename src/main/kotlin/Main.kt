interface Attachment {
    val type: String
}

data class PhotoAttachment(
    override val type: String = "photo",
    val photo: Photo
) : Attachment

data class VideoAttachment(
    override val type: String = "video",
    val video: Video
) : Attachment

data class AudioAttachment(
    override val type: String = "audio",
    val audio: Audio
) : Attachment

data class Post(
    val id: Int,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int? = null,
    val date: Long,
    val text: String,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean,
    val comments: Comments,
    val likes: Likes,
    val reposts: Reposts,
    val views: Views,
    val postType: String,
    val postSource: PostSource? = null,
    val attachments: List<Attachment>? = null,
    val geo: Geo? = null,
    val signerId: Int? = null,
    val copyHistory: List<Post>? = null,
    val canPin: Boolean,
    val canDelete: Boolean,
    val canEdit: Boolean,
    val isPinned: Boolean,
    val markedAsAds: Boolean,
    val isFavorite: Boolean,
    val postponedId: Int? = null
)

data class Photo(
    val id: Int,
    val ownerId: Int,
    val photo130: String,
    val photo604: String
)

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int,
    val url: String
)

data class Comments(
    val count: Int,
    val canPost: Boolean
)

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean
)

data class Reposts(
    val count: Int,
    val userReposted: Boolean
)

data class Views(
    val count: Int
)

data class PostSource(
    val type: String
)

data class Geo(
    val type: String,
    val coordinates: String,
    val place: Place? = null
)

data class Place(
    val id: Int,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val created: Int,
    val icon: String,
    val checkins: Int,
    val updated: Int,
    val type: Int,
    val country: String,
    val city: String,
    val address: String
)

class WallService {
    private var posts: MutableList<Post> = mutableListOf()

    fun addPost(post: Post) {
        posts.add(post)
    }

    fun updatePost(updatedPost: Post): Boolean {
        val index = posts.indexOfFirst { it.id == updatedPost.id }
        return if (index != -1) {
            posts[index] = updatedPost
            true
        } else {
            false
        }
    }

    fun removePostById(postId: Int): Boolean {
        val index = posts.indexOfFirst { it.id == postId }
        return if (index != -1) {
            posts.removeAt(index)
            true
        } else {
            false
        }
    }

    fun getPostById(postId: Int): Post? {
        return posts.find { it.id == postId }
    }
}
fun main() {
    val wallService = WallService()

    val photo = Photo(
        id = 1,
        ownerId = 1,
        photo130 = "https://vk.com/some_photo_link",
        photo604 = "https://vk.com/another_photo_link"
    )
    val photoAttachment = PhotoAttachment(photo = photo)

    val video = Video(
        id = 1,
        ownerId = 1,
        title = "A Funny Video",
        duration = 30
    )
    val videoAttachment = VideoAttachment(video = video)

    val audio = Audio(
        id = 1,
        ownerId = 1,
        artist = "Some Artist",
        title = "Some Song",
        duration = 180,
        url = "https://vk.com/some_audio_link"
    )
    val audioAttachment = AudioAttachment(audio = audio)

    val post = Post(
        id = 1,
        ownerId = 123,
        fromId = 456,
        date = System.currentTimeMillis(),
        text = "Привет, мир!",
        friendsOnly = false,
        comments = Comments(count = 0, canPost = true),
        likes = Likes(count = 0, userLikes = false, canLike = true),
        reposts = Reposts(count = 0, userReposted = false),
        views = Views(count = 0),
        postType = "post",
        canPin = true,
        canDelete = true,
        canEdit = true,
        isPinned = false,
        markedAsAds = false,
        isFavorite = false,
        attachments = listOf(photoAttachment, videoAttachment, audioAttachment)
    )

    wallService.addPost(post)

    val retrievedPost = wallService.getPostById(1)
    println(retrievedPost)

    val updatedPost = retrievedPost?.copy(text = "Привет, мир! Это обновленный пост.")
    if (updatedPost != null) {
        wallService.updatePost(updatedPost)
    }

    val removedPost = wallService.removePostById(1)
    println("Post removed: $removedPost")

    val removedPostAgain = wallService.removePostById(1)
    println("Post removed again: $removedPostAgain")
}
