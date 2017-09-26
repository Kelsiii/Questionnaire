var express = require('express');
var app = express();

// 允许所有的请求形式
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.use(express.static('public'));

// 引入json解析中间件
var bodyParser = require('body-parser');
// 添加json解析
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'local',
    database: 'test'
});


app.get('/', function(req, res) {
    res.sendFile(__dirname + '/view/index.html');
})
app.get('/content', function(req, res) {
    res.sendFile(__dirname + '/view/content.html');
})
app.get('/admin', function(req, res) {
    res.sendFile(__dirname + '/view/admin.html');
})

app.post('/submit', function(req, res) {

    var now = Date.now()
    var addSql = "INSERT INTO answer_sheet(answerId,studentId,questionairId,answer,submit_time,score_total,score_detail)" +
        "VALUES(?,?,1,?,?,?,?)";

    var studentId = "131250087" //学号 待更改

    var part1 = req.body.single.toString();
    var part2 = req.body.multiple.toString();
    var part3 = req.body.judgement.toString();
    var answers = part1 + ',' + part2 + ',' + part3; //record answer

    var answer_sheet = [req.body.single, req.body.multiple, req.body.judgement]
    var true_answer = [
        ['C', 'A', 'A', 'C', 'C', 'C', 'B', 'B', 'A', 'A'],
        ['ABCD', 'ABDE', 'ABCEFG', 'ABD', 'BCD', 'ACD', 'BCD', 'ABCDEF', 'ABCD', 'AD'],
        ['T', 'F', 'T', 'T', 'F', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'F', 'T', 'F', 'T', 'T', 'T', 'F']
    ]
    var scores = [],
        temp = [];
    var score_total = 0;

    for (var i = 0; i < true_answer[0].length; i++) {
        if (answer_sheet[0][i] == true_answer[0][i]) {
            temp.push(2)
            score_total = score_total + 2
        } else {
            temp.push(0)
        }
    }
    scores.push(temp)

    temp = []
    for (var i = 0; i < true_answer[1].length; i++) {
        if (answer_sheet[1][i] == true_answer[1][i]) {
            temp.push(4)
            score_total = score_total + 4
        } else {
            temp.push(0)
        }
    }
    scores.push(temp)

    temp = []
    for (var i = 0; i < true_answer[2].length; i++) {
        if (answer_sheet[2][i] == true_answer[2][i]) {
            temp.push(2)
            score_total = score_total + 2
        } else {
            temp.push(0)
        }
    }
    scores.push(temp)
    var score_detail = scores[0].toString() + ',' + scores[1].toString() + ',' + scores[2].toString();


    var myDate = new Date();
    var time = myDate.toLocaleString();

    var addSqlParams = [now, studentId, answers, time, score_total, score_detail];
    connection.query(addSql, addSqlParams, function(err, result) {
        if (err) {
            console.log('[INSERT ERROR] - ', err.message);
            return;
        }
    });

    res.send(score_total.toString());
});

app.post('/getScores', function(req, res) {
    var filter = req.body.filter
    var sql = "SELECT studentbase.studentId,studentName,department,major, score_total,submit_time " +
        "FROM studentbase LEFT JOIN answer_sheet ON studentbase.studentId = answer_sheet.studentId " +
        "WHERE studentbase.studentId regexp '^[a-z][a-z]15'";

    if (filter.department != "all") {
        sql = sql + " AND department = '" + filter.department + "'"
    }

    if (filter.score == "not_pass") {
        sql = sql + " AND score_total != 100"
    }

    if (filter.score == "unfilled") {
        sql = sql + " AND score_total IS NULL"
    }

    connection.query(sql, function(err, result) {
        if (err) {
            console.log('[SELECT ERROR] - ', err.message);
            res.send({ success: false, msg: err, data: null });
            return;
        }
        res.send({ 'success': true, 'msg': "获取用户列表成功", 'total': result.length, 'data': result });
    });
})

app.get('/getAllScores', function(req, res) {

    var sql = "SELECT studentbase.studentId,studentName,department,major, score_total,submit_time " +
        "FROM studentbase LEFT JOIN answer_sheet ON studentbase.studentId = answer_sheet.studentId  " +
        "WHERE studentbase.studentId regexp '^[a-z][a-z]15' ORDER BY submit_time desc"

    connection.query(sql, function(err, result) {
        if (err) {
            console.log('[SELECT ERROR] - ', err.message);
            res.send({ success: false, msg: err, data: null });
            return;
        }
        res.send({ 'success': true, 'msg': "获取用户列表成功", 'total': result.length, 'data': result });
    });

});

app.get('/getDepartments', function(req, res) {

    var sql = "SELECT name from department"
    connection.query(sql, function(err, result) {
        if (err) {
            console.log('[SELECT ERROR] - ', err.message);
            res.send({ success: false, msg: err, data: null });
            return;
        }
        res.send({ 'success': true, 'departments_list': result });
    });

})


var server = app.listen(8081, function() {

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)

})