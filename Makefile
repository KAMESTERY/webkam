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

commit:
	git add -A
	git commit -m "wip"
