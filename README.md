# webkam
Kamestery Web Application

## Git Setup

```{bash}
# Clone the main repository
git clone https://github.com/KAMESTERY/webkam.git my_webkam

# Now navigate to https://github.com/KAMESTERY/webkam and fork the remository
# Then add you fork as a remote
cd my_webkam
git remote add my_machinekam https://github.com/yourusername/webkam

# List your remotes
git remote
#-output-> my_webkam
#-output-> origin

# Make changes and push them to your fork
git push -u my_webkam master

# And navigate to https://github.com/yourusername/webkam and create a pull request

# Finally pull new changes from origin
git pull origin master

# That completes your git workflow for contributing to this project. Thank you!

```

## Setup

```{bash}

# Install Go and Clojure and Node
brew install go clojure node
# Ensure proper compilation toolchain
xcode-select --install # optional
# Install Delve
go get -u github.com/derekparker/delve/cmd/dlv

```

## Build Project with Make

```{bash}

# Run
make run

# Build UI
make build-ui

# Build
make build

```

## Webpack

```{bash}
# Install Dependencies
npm i
# Build Assets
npm run build

```

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
LOG_LEVEL=DEBUG ./kamestery.com

```

## Environments

* [dev](https://dev.kamestery.com)
* [stage](https://stage.kamestery.com)
* [prod](https://kamestery.com)


## Miscellaneous

[Figwheel with new Clojure 1.9 CLI tools](http://www.functionalbytes.nl/clojure/nodejs/figwheel/repl/clojurescript/cli/2017/12/20/tools-deps-figwheel.html)

