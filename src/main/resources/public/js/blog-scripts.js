function upVotePost(post_id){
    var url = '/posts/upvote';
    //construct div id
    var divId = "#" + "post_" + post_id;
    var post_info = {"PostId":String(post_id)};
    $(divId).load(url, post_info);
}

function downVotePost(post_id){
    var url = '/posts/downvote';
    //construct div id
    var divId = "#" + "post_" + post_id;
    var post_info = {"PostId":String(post_id)};
    $(divId).load(url, post_info);
}

function addComment(post_id){
    var divId = "#" + "post_" + post_id;
    var url = 'posts/add_comment';
    $(divId).post(url);
}

function editPost(post_id){

    var postDivId = "#" + "post_" + post_id;

    var postId = String(post_id);
    var postTitle = $(postDivId).find('#title').text();
    var postTags = $(postDivId).find('#tags').text();
    var postContent = $(postDivId).find('#content').text();

    var post_info = {
        "PostId" : post_id,
        "PostTitle" : postTitle,
        "PostTags" : postTags,
        "PostContet" : postContent
    }

    alert(postContent);


}