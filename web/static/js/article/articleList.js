function getArticleList(all) {
    if (all) {
        var ret = []
        for (var i = 0; i < 10; i++) {
            var single = {};
            single.title = "mock title" + i
            single.content = "mock content" + i
            single.author = "mock author" + i
            d = new Date()
            single.createTime = d.toLocaleDateString() + ' ' + d.toLocaleTimeString();
            single.modifiedTime = d.toLocaleDateString() + ' ' + d.toLocaleTimeString();
            ret.push(single)
        }
        return ret
    } else {

    }
}