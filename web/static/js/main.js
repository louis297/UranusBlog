

function loginAction(){
    var username = $('#Username').val();
    var password = $('#Password').val();
    if(!username){
        alert("Please input username.");
        return;
    } else if (!password) {
        alert("Please input password.");
        return;
    }
    var dataStr = 'Username=' + username +'&Password=' + password;
    $.ajax({
        url: '/login',
        method: 'post',
        data: dataStr,
        success: function(ret) {
            console.log(ret);
        }
    })
}

$(function () {
    var loginBtn = $('#loginButton');
    loginBtn.click(loginAction);

    getArticleList(true)

})