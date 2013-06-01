#!/usr/bin/env ruby

require 'rubygems'
require 'json'
require 'net/http'
require 'net/https'

txt = `pbpaste`
msg = {'text' => txt}.to_json

def parse_config
    conf = open('parse.conf').readlines.map {|line| line.chomp.split(/\s+/)[1] }
    {'app_id' => conf.shift, 'api_key' => conf.shift}
end

conf = parse_config

uri = URI.parse('https://api.parse.com/1/functions/throw')
https = Net::HTTP.new(uri.host, uri.port)
https.use_ssl = true
req = Net::HTTP::Post.new(uri.path)


req["X-Parse-Application-Id"] = conf['app_id']
req["X-Parse-REST-API-Key"]   = conf['api_key']
req["Content-Type"] = 'application/json'
req.body = msg.to_s

res = https.request(req)
p res.body
