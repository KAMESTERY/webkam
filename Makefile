BASEDIR=$(PWD)

deps:
	rm -rf $(PWD)/node_modules; npm i

node-repl:
	npx shadow-cljs node-repl app

compile:
	npx shadow-cljs compile app web

release:
	npx shadow-cljs release app web

deploy:
	gcloud app deploy -v dev

webpack-dev:
	webpack -d
	cp $(BASEDIR)/dist/static/css/bundle.css $(BASEDIR)/static/css
	cp $(BASEDIR)/dist/static/js/bundle.js $(BASEDIR)/static/js
