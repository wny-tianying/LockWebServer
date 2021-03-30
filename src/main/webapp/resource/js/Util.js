//插入数据返回的信息解析
function insertReturnDataResolve(message) {
    var data = JSON.parse(message);
    if (data.happenError){
        //插入数据失败
        alert(data.data);
        return false
    }else {
        return true;
    }
}