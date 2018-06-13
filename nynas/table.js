function createTrainTable(rows, colm) {
    this.rows = rows;
    this.colm = colm;
    var table = document.getElementById('trainTable'),
        avgH = 9,
        ankH = 10,
        nr = 42;

    for (i = 1; i <= rows; i++) {
        row = table.insertRow();
        avgH = avgH + 1;
        ankH = ankH + 1;
      
        if (i === 2) {
            nr = 43;
        }
        if (i === 3) {
            nr = 42 + "X";
        }
      
        for (j = 1; j <= colm; j++) {
        
            if (j === 1) {
                row.insertCell().textContent = nr;
          
            }
            if (j === 2) {
                row.insertCell().textContent = avgH + ':25';
          
            } else if (j === 3) {
                row.insertCell().textContent = ankH + ':23';
          
            }
        }
    }
    document.appendChild(table);
}

function loadWeatherData(callback) {
    var ajax = new XMLHttpRequest();
    ajax.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var weatherData = JSON.parse(this.response);
            var weatherList = weatherData.list;
            callback(weatherList);
        }
    };

    var city = 'nynashamn';
    var apikey = '2a532252f961ec7f2129400ff2781dc9';
    ajax.open('GET', 'https://api.openweathermap.org/data/2.5/forecast?q=' + city +
              '&APPID=' + apikey, true);
    ajax.send();
}
  
function createWeatherTable(rows) {
    this.rows = rows;
    loadWeatherData(function (weatherList) {
        var table = document.getElementById('weatherTable');
    
        for (index = 0; index <= rows; index++) {
            row = table.insertRow();
            var time = weatherList[index].dt_txt;
            var date = new Date(time);
            var hour = date.getHours() + ':00';
            row.insertCell().textContent = hour;

            var vader = weatherList[index].weather[0].description;
            vader = vader.charAt(0).toUpperCase() + vader.slice(1);
            row.insertCell().textContent = vader;

            var temp = weatherList[index].main.temp - 273.15;
            temp = temp.toFixed(1) + '°C';
            row.insertCell().textContent = temp;

            var wind = weatherList[index].wind.speed;
            wind = wind.toFixed(1) + 'm/s';
            row.insertCell().textContent = wind;
    
        }
    
    });
    
    document.appendChild(table);
}

function addCityName() {
    document.getElementById("felmeddelende").innerHTML = "Inga problem i trafiken";
  
    var input = document.getElementById("cityInput").value;
    document.getElementById("city").innerHTML = "Åker ifrån: " + input;
}

function loadTrainTable() {
    addCityName();
    createTrainTable(3, 3);
}

function loadWeatherTable() {
    createWeatherTable(8);
}