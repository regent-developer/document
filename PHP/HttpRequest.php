<?php
class HttpRequest
{
	/**
     * GET请求
     * @param $url
     * @param $header
     * @return mixed
     */
    public static function getRequest($url, $header = null) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        if(!empty($header)) {
            $headers = array();
            foreach ($header as $key => $value) {
                $headers[] = $key.': '.$value;
            }
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        }
        $result = curl_exec($ch);
        $error = curl_error($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);
        if (!empty($error)) {
            return [
                'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
                'code' => $httpCode,
                'msg' => $error,
                'response' => json_decode($result, true)
            ];
        }

        return [
            'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
            'code' => $httpCode,
            'msg' => 'request success',
            'response' => json_decode($result, true)
        ];
    }


    /**
     * POST请求
     * @param $url
     * @param $data
     * @param $header
     * @return mixed
     */
    public static function postRequest($url, $data, $header = null) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_POST, 1);

        if (!empty($header['Content-Type']) && stripos($header['Content-Type'], 'json') !== false) {
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        } else {
            curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($data));
        }

        if(!empty($header)) {
            $headers = array();
            foreach ($header as $key => $value) {
                $headers[] = $key.': '.$value;
            }
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        }
        $result = curl_exec($ch);
        $error = curl_error($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);

        if (!empty($error)) {
            return [
                'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
                'code' => $httpCode,
                'msg' => $error,
                'response' => json_decode($result, true)
            ];
        }

        return [
            'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
            'code' => $httpCode,
            'msg' => 'request success',
            'response' => json_decode($result, true)
        ];
    }


    /**
     * PUT请求
     * @param $url
     * @param $data
     * @param $header
     * @return mixed
     */
    public static function putRequest($url, $data, $header = null) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");

        if (stripos($header['Content-Type'], 'json') !== false) {
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        } else {
            curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($data));
        }

        if(!empty($header)) {
            $headers = array();
            foreach ($header as $key => $value) {
                $headers[] = $key.': '.$value;
            }
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        }
        $result = curl_exec($ch);
        $error = curl_error($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);
        if (!empty($error)) {
            return [
                'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
                'code' => $httpCode,
                'msg' => $error,
                'response' => json_decode($result, true)
            ];
        }

        return [
            'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
            'code' => $httpCode,
            'msg' => 'request success',
            'response' => json_decode($result, true)
        ];
    }


    /**
     * DELETE请求
     * @param $url
     * @param $data
     * @param $header
     * @return mixed
     */
    public static function deleteRequest($url, $data = null, $header = null) {
        /*if (empty($header)) {
            $header = [
                'Authorization' => 'Bearer xxxxxxxxxxx',
                'Content-Type' => 'application/json',
            ];
        }*/

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "DELETE");
        if (!empty($data)) {
            if (stripos($header['Content-Type'], 'json') !== false) {
                curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
            } else {
                curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($data));
            }
        }
        if (!empty($header)) {
            $headers = array();
            foreach ($header as $key => $value) {
                $headers[] = "{$key}: {$value}";
            }
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        }
        $result = curl_exec($ch);
        $error = curl_error($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);
        if (!empty($error)) {
            return [
                'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
                'code' => $httpCode,
                'msg' => $error,
                'response' => json_decode($result, true)
            ];
        }

        return [
            'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
            'code' => $httpCode,
            'msg' => 'request success',
            'response' => json_decode($result, true)
        ];
    }


    /**
     * 下载请求
     * @param $url
     * @param $localFile
     * @return array
     */
    public static function downLoadRequest($url, $localFile) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        $file = fopen($localFile, 'w');
        curl_setopt($ch, CURLOPT_FILE, $file);
        curl_setopt($ch, CURLOPT_HEADER, 0);
        $result = curl_exec($ch);
        $error = curl_error($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);
        fclose($file);

        if (!empty($error)) {
            return [
                'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
                'code' => $httpCode,
                'msg' => $error,
                'response' => json_decode($result, true)
            ];
        }

        return [
            'success' => !empty($httpCode) && preg_match("/^(2|3)\d{2}$/", $httpCode) ? true : false,
            'code' => $httpCode,
            'msg' => '',
            'response' => json_decode($result, true)
        ];
    }
}

