var app = angular.module( 'financeModule',[]);

var month = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sep", "Oct", "Nov", "Dec"];

app.controller('financeCTRL', function($scope, $http){
    $scope.getNutrition = function () {
        $http.get('https://api.nutritionix.com/v1_1/search/' + $scope.food + '?results=0:1&fields=*&appId=ebef0451&appKey=1fb3db833bdba1e32c1a474ce5e7c2c8').success(function(data) {
            //Below call to console.log() for debugging
            console.log(data);
            $scope.calories=data.hits[0].fields.nf_calories + " cal";
            $scope.servingWt=data.hits[0].fields.nf_serving_weight_grams + " g";

        })

    };

    $scope.getPrediction = function(){
        var val = String($scope.date);
        console.log(val);
        var mon = "";
        var arr = val.split(" ");
        switch(arr[1]){
            case month[0]:
                mon = "01";
                break;
            case month[1]:
                mon = "02";
                break;
            case month[2]:
                mon = "03";
                break;
            case month[3]:
                mon = "04";
                break;
            case month[4]:
                mon = "05";
                break;
            case month[5]:
                mon = "06";
                break;
            case month[6]:
                mon = "07";
                break;
            case month[7]:
                mon = "08";
                break;
            case month[8]:
                mon = "09";
                break;
            case month[9]:
                mon = "10";
                break;
            case month[10]:
                mon = "11";
                break;
            case month[11]:
                mon = "12";
                break;
            case month[12]:
                mon = "01";
                break;
        }
        var day = arr[2];
        var year = arr[3];

        console.log(year+"-"+mon+"-"+day );
        var searchData = { "date": year+"-"+mon+"-"+day };
        var req = $http.post('http://127.0.0.1:8081/search',searchData);

        req.success(function(data) {
            console.log(data);

            document.getElementById("prediction").innerHTML = data.prediction[0].prediction;

        });
        req.error(function(data) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });
    };

    $scope.myFunction = function() {
        var x = document.getElementById("myDate").value;
        document.getElementById("demo").innerHTML = x;
    };
});
