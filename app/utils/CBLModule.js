/**
 * @providesModule CBLModule
 */

'use strict';

/**
 * This exposes the native CBLModule module as a JS module. This has a function 'getUrl'
 * to get CBL url;
 */
var { NativeModules } = require('react-native');
module.exports = NativeModules.CBLModule;