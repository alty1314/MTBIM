let viewToken = '9eeaced2e1e046e4bc71bb5c16381140';
let viewer3D;
      let app;
      // 配置JSSDK加载项
      window.onload = function() {
        let loaderConfig = new BimfaceSDKLoaderConfig();
        loaderConfig.viewToken = viewToken;
        BimfaceSDKLoader.load(loaderConfig, successCallback, failureCallback);
      }
      // 加载成功回调函数
      function successCallback(viewMetaData) {
        let dom4Show = document.getElementById('domId');
        // 设置WebApplication3D的配置项
        let webAppConfig = new Glodon.Bimface.Application.WebApplication3DConfig();
        webAppConfig.domElement = dom4Show;
        // 创建WebApplication3D，用以显示模型
        app = new Glodon.Bimface.Application.WebApplication3D(webAppConfig);  
        app.addView(viewToken);
        viewer3D = app.getViewer();
      }
      // 加载失败回调函数
      function failureCallback(error) {
        console.log(error);
      }