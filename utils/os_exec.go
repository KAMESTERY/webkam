package utils

import (
	"io"
	"os"
	"os/exec"
)

var osexec_logger = NewLogger("utilsosexec")

func ExecBin(relativeBinPath string, args ...string) {
	fullBinPath := baseDir + "/" + relativeBinPath
	if _, err := os.Stat(fullBinPath); os.IsNotExist(err) {
		osexec_logger.Fatalf("FILE_DOES_NOT_EXISTS_ERROR:::: %s", fullBinPath)
		return
	}
	osexec_logger.Infof("............fullBinPath......====>.......%s.............................................", fullBinPath)
	exec.Command("cp", fullBinPath, "/tmp").Start()

	osexec_logger.Info("......................................................................")

	execBinPath := "/tmp/"+relativeBinPath
	osexec_logger.Infof("..........execBinPath......====>.......%s...............................................", execBinPath)
	cmd := exec.Command(execBinPath, args...)

	osexec_logger.Info("......................................................................")
	// Create stdout, stderr streams of type io.Reader
	stdout, err := cmd.StdoutPipe()
	checkError(err)
	stderr, err := cmd.StderrPipe()
	checkError(err)

	// Start command
	err = cmd.Start();
	checkError(err)

	defer cmd.Wait()  // Doesn't block

	// Non-blockingly echo command output to terminal
	go io.Copy(os.Stdout, stdout)
	go io.Copy(os.Stderr, stderr)
}

func checkError(err error) {
	if err != nil {
		osexec_logger.Fatalf("ERROR:::: %+v", err)
	}
}
