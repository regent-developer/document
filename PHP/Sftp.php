<?php
namespace App\Utils;

class Sftp
{
    private $conn;
    private $sftp;

    public function __construct($host, $port, $username, $password, $localDir = '.', $remoteDir = '/')
    {
        $this->conn = \ssh2_connect($host, $port);
        \ssh2_auth_password($this->conn, $username, $password);
        $this->sftp = \ssh2_sftp($this->conn);
        $this->localDir = rtrim($localDir, '/');
        $this->remoteDir = rtrim($remoteDir, '/');
    }


    public function download($remoteFile, $localFile)
    {
        if (!copy("ssh2.sftp://{$this->sftp}{$remoteFile}", $localFile)) {
            throw new \Exception('Unable to download file.');
        }
    }


    public function upload($localFile, $remoteFile, $overwrite = true)
    {
        if ($overwrite || !$this->fileExists($remoteFile)) {
            if (!copy($localFile, "ssh2.sftp://{$this->sftp}{$remoteFile}")) {
                throw new \Exception('Unable to upload file.');
            }
        } else {
            throw new \Exception('File already exists on remote server.');
        }
    }


    public function delete($remoteFile)
    {
        if (!$this->fileExists($remoteFile)) {
            throw new \Exception('File does not exist on remote server.');
        }

        if (!unlink("ssh2.sftp://{$this->sftp}{$remoteFile}")) {
            throw new \Exception('Unable to delete file.');
        }
    }


    public function copy($srcFile, $destFile, $overwrite = true)
    {
        if (!$this->fileExists($srcFile)) {
            throw new \Exception('Source file does not exist on remote server.');
        }

        if ($overwrite || !$this->fileExists($destFile)) {
            if (!copy("ssh2.sftp://{$this->sftp}{$srcFile}", "ssh2.sftp://{$this->sftp}{$destFile}")) {
                throw new \Exception('Unable to copy file.');
            }
        } else {
            throw new \Exception('Destination file already exists on remote server.');
        }
    }


    public function move($srcFile, $destFile, $overwrite = true)
    {
        if (!$this->fileExists($srcFile)) {
            throw new \Exception('Source file does not exist on remote server.');
        }

        if ($overwrite || !$this->fileExists($destFile)) {
            if (!rename("ssh2.sftp://{$this->sftp}{$srcFile}", "ssh2.sftp://{$this->sftp}{$destFile}")) {
                throw new \Exception('Unable to move file.');
            }
        } else {
            throw new \Exception('Destination file already exists on remote server.');
        }
    }


    public function backup($srcFile, $backupFile, $suffix = '_backup')
    {
        if (!$this->fileExists($srcFile)) {
            throw new \Exception('File does not exist on remote server.');
        }

        if ($this->fileExists($backupFile)) {
            throw new \Exception('Backup file already exists on remote server.');
        }

        if (!copy("ssh2.sftp://{$this->sftp}{$srcFile}", "ssh2.sftp://{$this->sftp}{$backupFile}")) {
            throw new \Exception('Unable to create backup file.');
        }
    }


    public function listDirectory($dir) {
        $handle = opendir("ssh2.sftp://{$this->sftp}{$dir}");
        $files = array();
        while (($file = readdir($handle)) !== false) {
            if ($file != '.' && $file != '..') {
                $files[] = $file;
            }
        }
        closedir($handle);
        return $files;
    }


    public function fileExists($remoteFile)
    {
        return file_exists("ssh2.sftp://{$this->sftp}{$remoteFile}");
    }


    public function __destruct()
    {
        \ssh2_disconnect($this->conn);
    }
}

