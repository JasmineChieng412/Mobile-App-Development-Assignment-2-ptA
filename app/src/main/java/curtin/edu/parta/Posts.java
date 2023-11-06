package curtin.edu.parta;

public class Posts {

    private String postId, userId, postTitle, postBody;

    public Posts(){

    }

    public Posts(String postId, String userId, String postTitle, String postBody){
        this.postBody = postBody;
        this.postId = postId;
        this.userId = userId;
        this.postTitle = postTitle;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId(){
        return userId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

}
