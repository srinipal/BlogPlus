function upVotePost(post_id){
    var url = '/posts/upvote/';
    url = url + String(post_id);
    //construct div id
    var divId = "#" + "post_" + post_id;
    $(divId).load(url);
}

function downVotePost(post_id){
    var url = '/posts/downvote/';
    url = url + String(post_id);
    //construct div id
    var divId = "#" + "post_" + post_id;
    $(divId).load(url);
}