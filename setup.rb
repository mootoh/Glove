#!/usr/bin/env ruby

begin
    conf = open('parse.conf').readlines.map {|line| line.chomp.split(/\s+/)[1] }
rescue
    puts '!'
    puts '!  No parse.conf found.'
    puts '!  Create your own parse.conf file from parse.conf-template.'
    puts '!'
    exit 1
end

app_id = conf.shift
api_key = conf.shift
client_key = conf.shift
master_key = conf.shift

def replace_android(app_id, client_key)
    string_xml = open('GloveAndroid/GloveAndroid/src/main/res/values/strings.xml', 'r')
    replaced = string_xml.collect do |line|
        if line =~ /__PARSE_APP_ID/
            line.sub(/__PARSE_APP_ID/, app_id) 
        elsif line =~ /__PARSE_CLIENT_KEY/
            line.sub(/__PARSE_CLIENT_KEY/, client_key)
        else
            line
        end
    end

    open('GloveAndroid/GloveAndroid/src/main/res/values/strings.xml', 'w').puts replaced
end

def replace_mac(app_id, api_key)
    header = open('GloveMac/GloveMac/ParseCredential.h')
    replaced = header.collect do |line|
        if line =~ /__PARSE_APP_ID/
            line.sub(/__PARSE_APP_ID/, app_id)
        elsif line =~ /__PARSE_REST_API_KEY/
            line.sub(/__PARSE_REST_API_KEY/, api_key)
        else
            line
        end
    end

    open('GloveMac/GloveMac/ParseCredential.h', 'w').puts replaced
end

def replace_server(app_id, master_key)
    header = open('server_side/config/global.json')
    replaced = header.collect do |line|
        if line =~ /__PARSE_APP_ID/
            line.sub(/__PARSE_APP_ID/, app_id)
        elsif line =~ /__PARSE_MASTER_KEY/
            line.sub(/__PARSE_MASTER_KEY/, master_key)
        else
            line
        end
    end

    open('server_side/config/global.json', 'w').puts replaced

end


replace_android(app_id, client_key)
replace_mac(app_id, api_key)
replace_server(app_id, master_key)
#puts app_id
#puts api_key
#puts client_key
