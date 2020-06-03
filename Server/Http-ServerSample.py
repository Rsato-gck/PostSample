#!/usr/bin/env python3
# -*- coding: shift-jis -*-
import http.server as s
import json

class MyHandler(s.BaseHTTPRequestHandler):
    def do_POST(self):

        # リクエスト取得
        content_len  = int(self.headers.get("content-length"))
        body = json.loads(self.rfile.read(content_len).decode('utf-8'))
        
        if body["command"] == "L001":
            body["result"] = ["OK"]
            body["user"] = "user"
            
        if body["command"] == "L002":
            body["result"] = ["Category01", "Category02"]
        
        # レスポンス処理
        self.send_response(200)
        self.send_header('Content-type', 'application/json;charset=utf-8')
        self.end_headers()
        body_json = json.dumps(body, sort_keys=False, indent=4, ensure_ascii=False) 
        self.wfile.write(body_json.encode("utf-8"))

# サーバ起動
host = '0.0.0.0'
port = 3333
httpd = s.HTTPServer((host, port), MyHandler)
httpd.serve_forever()