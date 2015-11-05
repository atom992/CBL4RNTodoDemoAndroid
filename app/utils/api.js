var CBLModule = require('CBLModule');
var url ='http://127.0.0.1:5984/';
{
                    CBLModule.getUrl(
                          (err) => {
                              console.log('------ callback error--------' + err);
                          },
                          (msg) => {
                            console.log('------ url--------' + msg);
                              url = msg;
                          }
                    )

}

var api = {
    url: url,

    saveTodo(title){
        return fetch(this.url + 'todos/', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                type: 'list',
                title: title
            })
        }).then((res) => res.json());
    },



    getTodos(){
            return fetch(this.url + 'todos/_all_docs?include_docs=true').then((response) => {
              if (response.status !== 200) {

                return fetch(this.url + 'todos', {
                                            method:'PUT',
                                            headers: {
                                                'Accept': 'application/json',
                                                'Content-Type': 'application/json'
                                            },
                                            body: JSON.stringify({ok:true})
                                        }).then((res) => res.json());
              }
              return response.json();
            })
        },

    startSync(){
        return fetch(this.url + '_replicate', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                source: 'todos',
                target: 'http://localhost:4984/todos/',
                continuous: true
            })
        }).then((res) => res.json());
    }
};

module.exports = api;



