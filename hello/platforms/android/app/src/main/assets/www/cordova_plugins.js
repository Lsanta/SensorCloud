cordova.define('cordova/plugin_list', function(require, exports, module) {
  module.exports = [
    {
      "id": "cordova-plugin-fcm.FCMPlugin",
      "file": "plugins/cordova-plugin-fcm/www/FCMPlugin.js",
      "pluginId": "cordova-plugin-fcm",
      "clobbers": [
        "FCMPlugin"
      ]
    },
    {
      "id": "com-darryncampbell-cordova-plugin-intent.IntentShim",
      "file": "plugins/com-darryncampbell-cordova-plugin-intent/www/IntentShim.js",
      "pluginId": "com-darryncampbell-cordova-plugin-intent",
      "clobbers": [
        "intentShim"
      ]
    },
    {
      "id": "com.napolitano.cordova.plugin.intent.IntentPlugin",
      "file": "plugins/com.napolitano.cordova.plugin.intent/www/android/IntentPlugin.js",
      "pluginId": "com.napolitano.cordova.plugin.intent",
      "clobbers": [
        "IntentPlugin"
      ]
    },
    {
      "id": "cordova-plugin-dialogs.notification",
      "file": "plugins/cordova-plugin-dialogs/www/notification.js",
      "pluginId": "cordova-plugin-dialogs",
      "merges": [
        "navigator.notification"
      ]
    },
    {
      "id": "cordova-plugin-dialogs.notification_android",
      "file": "plugins/cordova-plugin-dialogs/www/android/notification.js",
      "pluginId": "cordova-plugin-dialogs",
      "merges": [
        "navigator.notification"
      ]
    },
    {
      "id": "cordova-plugin-inappbrowser.inappbrowser",
      "file": "plugins/cordova-plugin-inappbrowser/www/inappbrowser.js",
      "pluginId": "cordova-plugin-inappbrowser",
      "clobbers": [
        "cordova.InAppBrowser.open",
        "window.open"
      ]
    },
    {
      "id": "cordova-plugin-velda-devicefeedback.DeviceFeedback",
      "file": "plugins/cordova-plugin-velda-devicefeedback/DeviceFeedback.js",
      "pluginId": "cordova-plugin-velda-devicefeedback",
      "clobbers": [
        "window.plugins.deviceFeedback"
      ]
    }
  ];
  module.exports.metadata = {
    "cordova-plugin-whitelist": "1.3.3",
    "cordova-plugin-fcm": "2.1.2",
    "com-darryncampbell-cordova-plugin-intent": "1.1.7",
    "com.napolitano.cordova.plugin.intent": "0.1.2",
    "cordova-plugin-dialogs": "2.0.1",
    "cordova-plugin-inappbrowser": "3.0.0",
    "cordova-plugin-velda-devicefeedback": "0.0.2"
  };
});