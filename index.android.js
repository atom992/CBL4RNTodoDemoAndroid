/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');
var Home = require('./app/components/Home');
var {
  AppRegistry,
  StyleSheet,
  Text,
  View
} = React;


var styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#111111'
  }
});


var CBL4RNTodoDemoAndroid = React.createClass({
  render: function() {
    return (
      <Home/>
    );
  }
});

AppRegistry.registerComponent('CBL4RNTodoDemoAndroid', () => CBL4RNTodoDemoAndroid);
