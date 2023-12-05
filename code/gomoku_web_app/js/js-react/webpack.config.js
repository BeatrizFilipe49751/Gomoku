const path = require('path')

module.exports = {
    mode: 'development',
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
      },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
            {
                    test: /\.css$/,
                    use: ['style-loader', 'css-loader'],
                  },
        ],
    }, 

    devServer: {
        static: path.resolve(__dirname, 'dist'),
        proxy: {
            '/api': 'http://localhost:3000',
        },
    },
};