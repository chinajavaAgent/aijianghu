import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import * as path from 'path';
import Components from 'unplugin-vue-components/vite';
import { VantResolver } from 'unplugin-vue-components/resolvers';
// https://vitejs.dev/config/
export default defineConfig({
    base: '/aijianghu/',
    plugins: [
        vue(),
        Components({
            resolvers: [VantResolver()],
        }),
    ],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src')
        }
    },
    server: {
        port: 3000,
        host: true,
        proxy: {
            '/aiGroup': {
                target: 'http://localhost:8080',
                changeOrigin: true
            }
        }
    },
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: "@import \"@/styles/variables.scss\";"
            }
        }
    },
    build: {
        outDir: 'dist',
        assetsDir: 'assets',
        rollupOptions: {
            output: {
                chunkFileNames: 'assets/js/[name]-[hash].js',
                entryFileNames: 'assets/js/[name]-[hash].js',
                assetFileNames: function (assetInfo) {
                    var info = assetInfo.name.split('.');
                    var extType = info[info.length - 1];
                    if (/\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/i.test(assetInfo.name)) {
                        return "assets/media/[name]-[hash][extname]";
                    }
                    else if (/\.(png|jpe?g|gif|svg|ico|webp)(\?.*)?$/i.test(assetInfo.name)) {
                        return "assets/img/[name]-[hash][extname]";
                    }
                    else if (/\.(woff2?|eot|ttf|otf)(\?.*)?$/i.test(assetInfo.name)) {
                        return "assets/fonts/[name]-[hash][extname]";
                    }
                    return "assets/".concat(extType, "/[name]-[hash][extname]");
                }
            }
        },
        sourcemap: true,
        assetsInlineLimit: 4096,
        cssCodeSplit: true,
        manifest: true
    }
});
