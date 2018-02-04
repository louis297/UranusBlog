var lock = true;
var currentBlogList = 'all';
var ARTICLES_PER_PAGE = 10;
var uid;

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
        url: '/api/login',
        method: 'post',
        data: dataStr,
        dataType: 'json',
        success: function(ret) {
            if(ret.result !== 'success'){
                $('#loginFailedMessage').html("Please check your username and password!")
                $('#loginFailedBanner').show();
            } else {
                $('#loginFailedBanner').hide();
            }
            isLogged();
        },
        error: function(){
            $('#loginFailedMessage').html("Network or server issue!")
            $('#loginFailedBanner').show();
            isLogged();
        }
    })
}

function updateLoginForm(success, ret){
    if(success){
        // display username and logout button
        $('#loginForm').html('<button class="btn btn-outline-success mr-sm-1 my-2 my-sm-1" type="button" id="btnAccountManage" onclick="accountManageButtonClicked();">Account Manage</button>\n' +
            '            <button class="btn btn-outline-danger mr-sm-1 my-2 my-sm-1" type="button" id="btnLogout" onclick="logoutButtonClicked();">Logout</button>')

        // display the "my blog" tag
        $('#ownBLog').show();
    } else {
        $('#ownBLog').hide();
        // display login form
        $('#loginForm').html('<input class="form-control mr-sm-1 my-2 my-sm-1" type="text" name="Username" placeholder="Username" aria-label="Username" id="Username">\n' +
            '            <input class="form-control mr-sm-1 my-2 my-sm-1" type="text" name="Password" placeholder="Password" aria-label="Password" id="Password">\n' +
            '<div style="width:100%; height=5px;"></div>' +
            '            <!--in main.js add ajax call for login-->\n' +
            '            <button class="btn btn-outline-success mr-sm-1 my-2 my-sm-1" type="button" id="loginButton" onclick="loginButtonClicked()">Login</button>\n' +
            '            <!-- popup modal for register -->\n' +
            //  data-toggle="modal" data-target="#registerModal"
            '            <button class="btn btn-outline-info mr-sm-1 my-2 my-sm-1" type="button" id="registerButton" onclick="window.location.href=\'/register.html\';">Register</button>');
    }
}
function isLogged(){
    $.ajax({
        url: '/api/isLogged',
        method: 'get',
        dataType: 'json',
        success: function(ret){
            console.log(ret);
            if(ret.is_logged === true){
                uid = ret.uid;
                updateLoginForm(true, ret);
            } else {
                uid = 0;
                updateLoginForm(false, ret);
            }
        },
        error: function(ret){
            console.log(ret)
            updateLoginForm(false, ret);
        }
    });
}

function loginButtonClicked(){
    loginAction();
}

function logoutButtonClicked(){
    // call /logout
    $.ajax({
        url: '/api/logout',
        method: 'get',
        success: function(){
            updateLoginForm(false, null);
        }
    })
}

function accountManageButtonClicked(){
    window.location.href='user_management.html?uid=' + uid;
}

$(function () {
    $('#allBlogBtn').click(function(){

        if(!lock && currentBlogList !=='all') {
            lock = true;
            $('#ownBlog').removeClass('active');
            $('#allBlog').addClass('active');
            currentBlogList = 'all';
            getArticleList('false')
        }
    });
    $('#ownBlogBtn').click(function(){

        if(!lock && currentBlogList !=='own') {
            lock = true;
            $('#allBlog').removeClass('active');
            $('#ownBlog').addClass('active');
            currentBlogList = 'own';
            getArticleList('true')
        }
    });

    $('#btnRegister').click(function(){
        var data = {};
        data.username = $('#regUsername').val();
        data.password = $('#regPassword').val();
        data.firstname = $('#regFirstname').val();
        data.lastname = $('#regLastname').val();
        data.middlename = $('#regMiddlename').val();
        data.email = $('#regEmail').val();
        data.nation = $('#regNation').val();
        data.birthday = $('#regBirthday').val();
        console.log(data);
        $.ajax({
            url:'/api/register',
            method:'post',
            data:data,
            dataType:'json',
            success:function(ret){
                // jump to main page
                if(ret.result=="success"){
                    $('#loginFailedBanner').hide();
                    window.location.href = '/main.html';
                } else {
                    // register error
                    $('#registerModal').modal();
                    $('#loginFailedMessage').html(ret.reason);
                    $('#loginFailedBanner').show();
                }
            },
            error:function(){
                // show error message
                $('#loginFailedMessage').html("Server error, please try later...")
                $('#loginFailedBanner').show();
            }
        });

    });

    $('#btnUploadAvatar').click(function(){
        $.ajax({
        url: '/api/register',
        method: 'post',
        data: data,
        dataType: 'json',
        success: function (ret) {

        }
        });
    });

    isLogged();

    // var loginBtn = $('#loginButton');
    // loginBtn.click(loginAction);

    var searchParams = new URLSearchParams(window.location.search)
    if (searchParams.has('own')){
        var own =searchParams.get('own')
    } else {
        var own = false;
    }
    console.log(own)
    getArticleList(own)
})