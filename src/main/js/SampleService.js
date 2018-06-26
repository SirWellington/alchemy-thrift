/*
 * Copyright © 2018. Sir Wellington.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//
// Autogenerated by Thrift Compiler (0.11.0)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


//HELPER FUNCTIONS AND STRUCTURES

sir.wellington.alchemy.thrift.generated.SampleService_request_args = function(args) {
  this.request = null;
  if (args) {
    if (args.request !== undefined && args.request !== null) {
      this.request = new sir.wellington.alchemy.thrift.generated.SampleRequest(args.request);
    }
  }
};
sir.wellington.alchemy.thrift.generated.SampleService_request_args.prototype = {};
sir.wellington.alchemy.thrift.generated.SampleService_request_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.request = new sir.wellington.alchemy.thrift.generated.SampleRequest();
        this.request.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

sir.wellington.alchemy.thrift.generated.SampleService_request_args.prototype.write = function(output) {
  output.writeStructBegin('SampleService_request_args');
  if (this.request !== null && this.request !== undefined) {
    output.writeFieldBegin('request', Thrift.Type.STRUCT, 1);
    this.request.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

sir.wellington.alchemy.thrift.generated.SampleService_request_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = new sir.wellington.alchemy.thrift.generated.SampleResponse(args.success);
    }
  }
};
sir.wellington.alchemy.thrift.generated.SampleService_request_result.prototype = {};
sir.wellington.alchemy.thrift.generated.SampleService_request_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new sir.wellington.alchemy.thrift.generated.SampleResponse();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

sir.wellington.alchemy.thrift.generated.SampleService_request_result.prototype.write = function(output) {
  output.writeStructBegin('SampleService_request_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

sir.wellington.alchemy.thrift.generated.SampleServiceClient = function(input, output) {
    this.input = input;
    this.output = (!output) ? input : output;
    this.seqid = 0;
};
sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype = {};
sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype.request = function(request, callback) {
  this.send_request(request, callback);
  if (!callback) {
    return this.recv_request();
  }
};

sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype.send_request = function(request, callback) {
  this.output.writeMessageBegin('request', Thrift.MessageType.CALL, this.seqid);
  var params = {
    request: request
  };
  var args = new sir.wellington.alchemy.thrift.generated.SampleService_request_args(params);
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_request();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype.recv_request = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new sir.wellington.alchemy.thrift.generated.SampleService_request_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'request failed: unknown result';
};
