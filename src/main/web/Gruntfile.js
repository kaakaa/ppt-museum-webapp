module.exports = function(grunt) {
  grunt.initConfig({
    browserify: {
      dist: {
        files: {
          'dist_packages/public/ppt-museum/pdfjs-controller/js/pdf.js-controller.js': ['index.js']
        }
      }
    },
    cssmin: {
      dist: {
        src: ['node_modules/pdf.js-controller/css/pdf-slide.css'],
        dest: 'dist_packages/public/ppt-museum/pdfjs-controller/css/pdf-slide.min.css'
      }
    },
    "bower-install-simple": {
      options: {
        directory: "components/public/ppt-museum"
      },
      "prod": {
        options: {
          production: true
        }
      },
      "dev": {
        options: {
          production: false
        }
      }
    }, 
    bower: {
      dev: {
        base: 'components/public/ppt-museum',
        dest: 'dist_packages/public/ppt-museum',
        options: {
          paths: {
            bowerDirectory: "components/public/ppt-museum"
          }
        }
      }
    },
    wiredep: {
      task: {
        src: ['../resources/templates/**/*.jade'],
        directory: 'components/public/ppt-museum',
        ignorePath: "../../web/components/public"
      }
    },
    copy: {
      main: {
        expand: true,
        cwd: 'dist_packages/public/ppt-museum/pdfjs-dist/cmaps',
        src: '*',
        dest: 'dist_packages/public/ppt-museum/cmaps/'
      }
    }
  });

  grunt.loadNpmTasks("grunt-browserify");
  grunt.loadNpmTasks("grunt-contrib-cssmin");
  grunt.loadNpmTasks("grunt-bower-install-simple");
  grunt.loadNpmTasks("main-bower-files");
  grunt.loadNpmTasks("grunt-wiredep");
  grunt.loadNpmTasks("grunt-contrib-copy");

  grunt.registerTask("default", "build");
  grunt.registerTask("build", ["browserify", "cssmin", "bower-install-simple", "bower", "copy", "wiredep"]);
};
