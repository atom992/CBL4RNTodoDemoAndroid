var api = {
  
  // 1
  localDatabaseUrl: 'http://localhost:5984',
  
  // 2
  remoteDatabaseUrl: 'http://localhost:4984',
 
  // 3
  saveTodo(title){
    return fetch(this.localDatabaseUrl + '/todos', {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        type: 'task',
        title: title
      })
    }).then((res) => res.json());
  },
  
  // 4
  getTodos(){
    return fetch(this.localDatabaseUrl + '/todos/_all_docs?include_docs=true').then((response) => {
      if (response.status !== 200) {
        return fetch(this.localDatabaseUrl + '/todos', {
          method: 'put',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ok: true})
        }).then((res) => res.json());
      }
      return response.json();
    })
  },

  // 5
  startSync(){
    return fetch(this.localDatabaseUrl + '/_replicate', {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        source: 'todos',
        target: this.remoteDatabaseUrl + '/todos',
        continuous: true
      })
    }).then((res) => res.json());
  }
  
};

module.exports = api;