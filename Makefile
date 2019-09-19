BASEDIR=$(PWD)

deps:
	rm -rf $(PWD)/node_modules; npm i

compile:
	npx shadow-cljs compile app web

node-repl:
	npx shadow-cljs node-repl app

watch-cljs:
	npx shadow-cljs watch app web

server-repl:
	nodemon start

release:
	npx shadow-cljs release app web

deploy:
	gcloud app deploy -v dev

webpack:
	webpack -d
	cp $(BASEDIR)/dist/static/css/bundle.css $(BASEDIR)/static/css
	cp $(BASEDIR)/dist/static/js/bundle.js $(BASEDIR)/static/js
