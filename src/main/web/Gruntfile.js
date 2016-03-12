module.exports = function(grunt) {
  grunt.initConfig({
    browserify: {
      dist: {
        files: {
          'dist_packages/js/pdf.js-controller.js': ['src/main/web/index.js']
        }
      }
    },
    cssmin : {
      dist: {
        src: ['node_modules/pdf.js-controller/css/pdf-slide.css'],
        dest: 'dist_packages/css/pdf-slide.min.css'
      }
    },
    wiredep: {
      task: {
        src: [
          '../resources/templates/**/*.jade'
        ]
      }
    }
  });

  grunt.loadNpmTasks("grunt-browserify");
  grunt.loadNpmTasks("grunt-contrib-cssmin");
  grunt.loadNpmTasks("grunt-wiredep");

  grunt.registerTask("default", "build");
  grunt.registerTask("build", ["browserify", "cssmin", "wiredep"])
};
