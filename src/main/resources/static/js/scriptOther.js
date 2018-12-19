$("#monitor").click(monitor);

function monitor(e) {
    console.log("monitor call!");

        setTimeout(getMember(), 10000);

}

function getMember() {
    console.log("getMember call")
    var url ="https://slack.com/api/conversations.members";
    var token = "?token=" + "xoxp-242583072180-433127751303-504671119504-f03c6434e2bdde6d4478518191a5e33e";
    var channel = "&channel=" + "CEUHXSS92";
    var option = "&pretty=1";

    var req = new XMLHttpRequest();
    req.open('GET', url + token + channel + option);
    req.send();

    req.onreadystatechange = function (e) {
      if (req.readyState === XMLHttpRequest.DONE) {
        if(req.status === 200) {
          console.log(req.responseText);
          let obj = JSON.parse(req.responseText);
          console.log(obj);
          obj.members.forEach(val => getMail(val));
        } else {
          console.log("Error!");
        }
      }
    };
}

function getMail(id) {
    console.log("Call getMail Method!");
    var url = "https://slack.com/api/users.profile.get";
    var token = "?token=xoxp-242583072180-433127751303-504671119504-f03c6434e2bdde6d4478518191a5e33e";
    var user = "&user=" + id;
    var option = "&pretty=1";

    var req = new XMLHttpRequest();
    req.open('GET', url + token + user + option);
    req.send();

    console.log(req.status);
    req.onreadystatechange = function (e) {
        if (req.readyState === XMLHttpRequest.DONE) {
            if(req.status === 200) {
                console.log(req.responseText);
                let obj = JSON.parse(req.responseText);
                console.log("오늘의 날짜 : " + getToday());
                console.log(obj.profile.email)
                //obj.profile.forEach(val => getCommitList(val.email, id, getToday(), getToday()));
                getCommitList(obj.profile.email, id, getToday(), getToday());

            } else {
            console.log("Error!");
            }
        }
    };
}

function getCommitList(email, id, from, to) {
    console.log("Call getCommentList");
    var url = "https://api.github.com/search/commits?q=";
    var email = "committer-email:" + email;
    var date = "+committer-date:" + from + ".." + to;
    var accept = "application/vnd.github.cloak-preview+json";
    var token = "6316c0629d901e913cd3639129b89487f73ccad2"
    var message = "내가 관심법으로 보자니! 그대의 머리에는 마구니가 가득찼어!";

    var req = new XMLHttpRequest();
    req.open('GET', url + email + date);
    req.setRequestHeader("Accept", accept);
    req.setRequestHeader("Authorization", token);
    req.send();

    req.onreadystatechange = function (e) {
      if (req.readyState === XMLHttpRequest.DONE) {
        if(req.status === 200) {
          let obj = JSON.parse(req.responseText);
          console.log(obj);
          if(Number(obj.total_count) < 1) {
            for(var i = 0; i < 10; i++) {
            sendMessage(email, id, message);
            }
          }
        } else {
          console.log("Error!");
        }
      }
    };
}

function sendMessage(email, id, message) {
    var url = "https://slack.com/api/chat.postMessage";
    var token = "?token=" + "xoxp-242583072180-433127751303-504671119504-f03c6434e2bdde6d4478518191a5e33e";
    var channel = "&channel=" + id;
    var text = "&text=" + message;
    var icon = "&icon_url=" + "https://mblogthumb-phinf.pstatic.net/MjAxODAzMjNfMjAx/MDAxNTIxNzc1NTE1MTE2.A-RidVWAGMo5CCEpc30lGKOG4OvEaKyU7Su3KM9eZW4g.WJF3uyRoYU67UBExG6iOQ-FEcNneqsx7Xua5T1m8BLQg.JPEG.kkangsniper7/20180323_121423.jpg?type=w800";
    var user_name = "&username=" + "마구니를쫓는자";
    var option = "&pretty=1";
    var req = new XMLHttpRequest();
    req.open('POST', url + token + channel + text + icon + user_name + option);
    req.send();

    req.onreadystatechange = function (e) {
      if (req.readyState === XMLHttpRequest.DONE) {
        if(req.status === 200) {
          console.log(email + " 에게 메세지 전송 완료!");
        } else {
          console.log("Error!");
        }
      }
    };
}

function getToday() {
    var d = new Date();
    return (d.getFullYear() + "-" + leadingZero(d.getMonth() + 1) + "-" + leadingZero(d.getDate()));
}

function leadingZero(now) {
    return now.length < 2 ? "0" + now : now;
}