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
    },
    {
      "id": "cordova-plugin-camera.Camera",
      "file": "plugins/cordova-plugin-camera/www/CameraConstants.js",
      "pluginId": "cordova-plugin-camera",
      "clobbers": [
        "Camera"
      ]
    },
    {
      "id": "cordova-plugin-camera.CameraPopoverOptions",
      "file": "plugins/cordova-plugin-camera/www/CameraPopoverOptions.js",
      "pluginId": "cordova-plugin-camera",
      "clobbers": [
        "CameraPopoverOptions"
      ]
    },
    {
      "id": "cordova-plugin-camera.camera",
      "file": "plugins/cordova-plugin-camera/www/Camera.js",
      "pluginId": "cordova-plugin-camera",
      "clobbers": [
        "navigator.camera"
      ]
    },
    {
      "id": "cordova-plugin-camera.CameraPopoverHandle",
      "file": "plugins/cordova-plugin-camera/www/CameraPopoverHandle.js",
      "pluginId": "cordova-plugin-camera",
      "clobbers": [
        "CameraPopoverHandle"
      ]
    },
    {
      "id": "cordova-plugin-imagepicker.ImagePicker",
      "file": "plugins/cordova-plugin-imagepicker/www/imagepicker.js",
      "pluginId": "cordova-plugin-imagepicker",
      "clobbers": [
        "plugins.imagePicker"
      ]
    },
    {
      "id": "org.apache.cordova.geolocation.Coordinates",
      "file": "plugins/org.apache.cordova.geolocation/www/Coordinates.js",
      "pluginId": "org.apache.cordova.geolocation",
      "clobbers": [
        "Coordinates"
      ]
    },
    {
      "id": "org.apache.cordova.geolocation.PositionError",
      "file": "plugins/org.apache.cordova.geolocation/www/PositionError.js",
      "pluginId": "org.apache.cordova.geolocation",
      "clobbers": [
        "PositionError"
      ]
    },
    {
      "id": "org.apache.cordova.geolocation.Position",
      "file": "plugins/org.apache.cordova.geolocation/www/Position.js",
      "pluginId": "org.apache.cordova.geolocation",
      "clobbers": [
        "Position"
      ]
    },
    {
      "id": "org.apache.cordova.geolocation.geolocation",
      "file": "plugins/org.apache.cordova.geolocation/www/geolocation.js",
      "pluginId": "org.apache.cordova.geolocation",
      "clobbers": [
        "navigator.geolocation"
      ]
    },
    {
      "id": "cordova-plugin-permission.Permission",
      "file": "plugins/cordova-plugin-permission/www/index.js",
      "pluginId": "cordova-plugin-permission",
      "clobbers": [
        "window.plugins.Permission"
      ]
    },
    {
      "id": "cordova-plugin-permission.tests",
      "file": "plugins/cordova-plugin-permission/tests/index.spec.js",
      "pluginId": "cordova-plugin-permission"
    }
  ];
  module.exports.metadata = {
    "cordova-plugin-whitelist": "1.3.3",
    "cordova-plugin-fcm": "2.1.2",
    "cordova-plugin-dialogs": "2.0.1",
    "cordova-plugin-inappbrowser": "3.0.0",
    "cordova-plugin-velda-devicefeedback": "0.0.2",
    "cordova-plugin-camera": "4.0.3",
    "cordova-plugin-imagepicker": "1.1.0",
    "org.apache.cordova.geolocation": "0.3.6",
    "cordova-plugin-permission": "0.1.0"
  };
});