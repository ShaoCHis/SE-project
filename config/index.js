module.exports = {
    dev: {

        // Paths
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        proxyTable: {
            '/api': {
                target: 'http://139.224.65.154:8080', // 你请求的第三方接口
                changeOrigin: true,
                secure:false,// 在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样服务端和服务端进行数据的交互就不会有跨域问题
                pathRewrite: {  // 路径重写，
                    '^/api': '/api'  // 替换target中的请求地址
                },
            },
        }
    }
}