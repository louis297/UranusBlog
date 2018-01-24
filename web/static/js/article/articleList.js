function getArticleList(all){
    if (all) {
        var ret = []
        for (var i = 0; i < 10; i++) {
            var single = {};
            single.title = "mock title" + i
            single.content = "mock content" + i
            single.author = "mock author" + i
            single.createTime = Date.now().toString()
            single.modifiedTime = Date.now().toString()
            ret.append(single)
        }
        return ret
    } else {

    }
}