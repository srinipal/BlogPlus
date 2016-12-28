const ADD_TAG_FORM = '<h2>Tags</h2>' +
                     'Separate tags with a comma(,)<br/>' +
                     '<input class="editPostTags" type="text" name="tags" id="tags" value="<%= TAGS %>" />' +
                     '<p><button onclick="updateTags(\'<%= POST_ID %>\')">Save</button></p>'



const EDIT_TAG_FORM = '<div class="editPostForm" id="post_<%= POST_ID %>" >' +
                      '    <p>Required fields are followed by <strong><abbr title="required">*</abbr></strong>.</p>' +
                      '    <h2>Title<strong><abbr title="required">*</abbr></strong></h2>' +
                      '    <input class="editPostTitle" type="text" id="title" name="title" value="<%= POST_TITLE %>"/>' +
                      '    <br/>' +
                      '    <h2>Blog Entry<strong><abbr title="required">*</abbr></strong></h2>' +
                      '    <textarea class="editPostBody" name="body" id="content" rows="20"><%= POST_CONTENT %></textarea>' +
                      '    <br/>' +
                      '    <h2>Tags</h2>' +
                      '    Separate tags with a comma(,)<br/>' +
                      '    <input class="editPostTags" type="text" name="tags" id="tags" value="<%= POST_TAGS %>"/>' +
                      '    <p><button onclick="savePost(\'<%= POST_ID %>\')">Save</button></p>' +
                      '</div>'

/**
    up-votes a blog post
**/
function upVotePost(postId){
    var url = '/blogplus/posts/upvote';
    //construct div id
    var divId = "#" + "post_" + postId;
    var postInfo = {"PostId" : String(postId)};
    $(divId).load(url, postInfo);

}

/**
    down-votes a blog post
**/
function downVotePost(postId){
    var url = '/blogplus/posts/downvote';
    //construct div id
    var divId = "#" + "post_" + postId;
    var postInfo = {"PostId" : String(postId)};
    $(divId).load(url, postInfo);
}

/**
    Prepares and loads an edit form for editing a blog post
**/
function preparePostEditForm(postId){

    var postDivId = "#" + "post_" + postId;

    postId = String(postId);
    var postTitle = $(postDivId).find('#title').text();
    var postTags = $(postDivId).find('#tags').text();
    var postContent = $(postDivId).find('#content').text();

    var editPostTemplate = _.template(EDIT_TAG_FORM)
    var editPostHTML = editPostTemplate({POST_ID : postId, POST_TITLE : postTitle, POST_CONTENT : postContent, POST_TAGS : postTags})
    $(postDivId).html(editPostHTML)
}

/**
    Prepares and loads an add tag form for adding tags to a blog post
**/
function prepareAddTagForm(postId, tags){
    var postDivId = "#" + "post_" + postId;
    var tagsDivId = postDivId + " #tagSection";
    var addTagTemplate = _.template(ADD_TAG_FORM)
    var addTagHTML = addTagTemplate({TAGS : tags, POST_ID : postId})

    //replace inner html
    $(tagsDivId).html(addTagHTML);
}

/**
    Updates the title/content/tags of a blog post in database
**/
function savePost(postId){
    var postDivId = "#" + "post_" + postId;

    postId = String(postId);

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

    var postInfo = {
        "PostId" : postId,
        "PostTitle" : postTitle,
        "PostTags" : postTags,
        "PostContent" : postContent
    };

    var url = "/blogplus/posts/save"
    $(postDivId).load(url, postInfo)
}

/**
    adds comment to a blog post
**/
function addComment(postId){
    var postDivId = "#" + "post_" + postId;

    var commentBody = $(postDivId + " :input[name=comment_body]").val();

    //trim and validate if comment is provided
    commentBody = commentBody.trim();
    if(commentBody == ""){
        alert("Please enter the comment");
        return;
    }

    var postInfo = {
        "PostId" : postId,
        "CommentBody" : commentBody
    };

    var url = "/blogplus/posts/addComment";
    $(postDivId).load(url, postInfo);
}

/**
    Updates the tags of a blog post in database
**/
function updateTags(postId){
    var postDivId = "#" + "post_" + postId;
    var postTags = $(postDivId + " #tagSection input[name=tags]").val();
    var postInfo = {
        "PostId" : postId,
        "PostTags" : postTags
    };

    var url = "/blogplus/posts/updateTags"
    $(postDivId).load(url, postInfo)
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


function tweetTheLink(postUrl, postTitle) {
    window.open('https://twitter.com/share?url=' + escape(postUrl) + '&text='+postTitle, '', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');
    return false;
}
