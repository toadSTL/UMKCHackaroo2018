var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');
var bodyParser = require("body-parser");
var express = require('express');
var cors = require('cors');
var app = express();
//mongodb://<dbuser>:<dbpassword>@ds151863.mlab.com:51863/finance-ai
var url = 'mongodb://toadSTL:toadSTL1@ds151863.mlab.com:51863/finance-ai';
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));



MongoClient.connect(url, function(err, database) {
    if (err) {
        console.log("Connection Failed, Error while connecting to Database");
        process.abort();
    } else {
        db = database;
    }

    // db.collection('financeData').insertMany(initPredictions, function(err, result){
    //     if(err){
    //         console.log("Failed to initialize collection 'financialData'.")
    //     }else{
    //         console.log("Successfully Initialized Collection 'financeData'.")
    //     }
    // });
});


app.post('/search', function (req, res, next) {
    console.log(req.body);
    var resultArray = [];
    var cursor = db.collection('financeData').find(req.body);
    cursor.forEach(function(doc, err) {
        assert.equal(null, err);
        console.log(doc);
        console.log("Search Results");
        console.log("date: " + doc.date);
        console.log("prediction: " + doc.prediction);
        resultArray.push(doc)
        }, function () {
            res.send({prediction: resultArray});
    });

});


var server = app.listen(8081,function () {
    var host = server.address().address;
    var port = server.address().port;
    console.log("Example app listening at http://%s:%s", host, port)
});