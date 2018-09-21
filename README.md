# webkam
Kamestery Web Application

## Clojurescript

```{bash}

# Get some help
clj --main cljs.main --help

# Get a REPL going
clj --main cljs.main --compile hello-world.core --repl

# Production Build
clj -m cljs.main -O advanced -o static/js/ui.js -c hello-world.core

```

## Build Project

```{bash}

# Clone Repository
git clone https://github.com/KAMESTERY/webkam.git

# Get inside project directory
cd /path/to/webkam

# Resolve dependencies
go mod vendor

# Build project
go build

# Run project
./kamestery.com

```

## Stage

[Click here to Visit Stage Environment](https://hllhvmvsfj.execute-api.us-east-1.amazonaws.com/staging/)


## Miscellaneous

[Figwheel with new Clojure 1.9 CLI tools](http://www.functionalbytes.nl/clojure/nodejs/figwheel/repl/clojurescript/cli/2017/12/20/tools-deps-figwheel.html)

