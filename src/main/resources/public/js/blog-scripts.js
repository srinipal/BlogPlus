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

function preparePostEditForm(post_id){

    var postDivId = "#" + "post_" + post_id;

    var postId = String(post_id);
    var postTitle = $(postDivId).find('#title').text();
    var postTags = $(postDivId).find('#tags').text();
    var postContent = $(postDivId).find('#content').text();

    var post_info = {
        "PostId" : post_id,
        "PostTitle" : postTitle,
        "PostTags" : postTags,
        "PostContent" : postContent
    }

    var url = "/posts/edit"
    $(postDivId).load(url, post_info)
}


function savePost(post_id){
    var postDivId = "#" + "post_" + post_id;

    var postId = String(post_id);

    var postContent = $(postDivId).find('#content').val();

    var postTitle = $(postDivId + " input[name=title]").val();

    var postTags = $(postDivId + " input[name=tags]").val();

    var post_info = {
        "PostId" : post_id,
        "PostTitle" : postTitle,
        "PostTags" : postTags,
        "PostContent" : postContent
    };

    var url = "/posts/save"
    $(postDivId).load(url, post_info)
}

function getAllValues(mainDiv){
    var inputValues = [];
        $(mainDiv + " input").each(function() {
            var type = $(this).attr("type");
            if ((type == "checkbox" || type == "radio") && this.checked) {
                inputValues.push($(this).val());
            }
            else if (type != "button" || type != "submit") {
                inputValues.push($(this).val());
            }
        })
        return inputValues.join(',');
}

function prepareForDisplay(str){
    return str.replace(/<br\s*\/?>/mg, "n");
}