var api = {
    url: 'http://127.0.0.1:5984',
    saveTodo(title){
        return fetch(this.url + '/todos/', {
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
            return fetch(this.url + '/todos/_all_docs?include_docs=true').then((response) => {
              if (response.status !== 200) {

                return fetch('http://127.0.0.1:5984/todos', {
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
        return fetch(this.url + '/_replicate', {
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



