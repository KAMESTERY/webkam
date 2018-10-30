
BASEDIR=$(PWD)

all: build-ui build

build-ui:
	npm i
	npm run build
	clj -m cljs.main -O advanced -o $(BASEDIR)/static/js/ui.js -c hello-world.core

build:
	go build

gen-code:
	@echo "Generating Go code..."
	@protoc -I svc/ svc/authkm.proto --go_out=plugins=grpc:grpc/gen

run: build-ui build
	$(BASEDIR)/kamestery.com
