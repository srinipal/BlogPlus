function upVotePost(post_id){
    var url = '/blogplus/posts/upvote';
    //construct div id
    var divId = "#" + "post_" + post_id;
    var post_info = {"PostId":String(post_id)};
    $(divId).load(url, post_info);
}

function downVotePost(post_id){
    var url = '/blogplus/posts/downvote';
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

    var url = "/blogplus/posts/edit"
    $(postDivId).load(url, post_info)
}


function savePost(post_id){
    var postDivId = "#" + "post_" + post_id;

    var postId = String(post_id);

    var postContent = $(postDivId).find('#content').val();
    //trim and validate if comment is provided
    postContent = postContent.trim();
    if(postContent == ""){
        alert("Please enter the postContent");
        return;
    }

    var postTitle = $(postDivId + " input[name=title]").val();
    //trim and validate if comment is provided
    postTitle = postTitle.trim();
    if(postTitle == ""){
        alert("Please enter the postTitle");
        return;
    }

    var postTags = $(postDivId + " input[name=tags]").val();

    var post_info = {
        "PostId" : post_id,
        "PostTitle" : postTitle,
        "PostTags" : postTags,
        "PostContent" : postContent
    };

    var url = "/blogplus/posts/save"
    $(postDivId).load(url, post_info)
}

function addComment(post_id){
    var postDivId = "#" + "post_" + post_id;

    var commentBody = $(postDivId + " :input[name=comment_body]").val();

    //trim and validate if comment is provided
    commentBody = commentBody.trim();
    if(commentBody == ""){
        alert("Please enter the comment");
        return;
    }

    var post_info = {
        "PostId" : post_id,
        "CommentBody" : commentBody
    };

    var url = "/blogplus/posts/addComment";
    $(postDivId).load(url, post_info);
}

function getAllValues(mainDiv){
    var inputValues = [];
        $(mainDiv + " :input").each(function() {
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


function textAreaAdjust(o) {
  o.style.height = "1px";
  o.style.height = (25+o.scrollHeight)+"px";
}
