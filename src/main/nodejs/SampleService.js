//
// Autogenerated by Thrift Compiler (0.9.2)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//
var thrift = require('thrift');
var Thrift = thrift.Thrift;
var Q = thrift.Q;


var ttypes = require('./Sample_types');
//HELPER FUNCTIONS AND STRUCTURES

sir.wellington.alchemy.thrift.generated.SampleService_request_args = function(args) {
  this.request = null;
  if (args) {
    if (args.request !== undefined) {
      this.request = args.request;
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
        this.request = new ttypes.SampleRequest();
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
    if (args.success !== undefined) {
      this.success = args.success;
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
        this.success = new ttypes.SampleResponse();
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

sir.wellington.alchemy.thrift.generated.SampleServiceClient = exports.Client = function(output, pClass) {
    this.output = output;
    this.pClass = pClass;
    this._seqid = 0;
    this._reqs = {};
};
sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype = {};
sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype.seqid = function() { return this._seqid; }
sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype.new_seqid = function() { return this._seqid += 1; }
sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype.request = function(request, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_request(request);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_request(request);
  }
};

sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype.send_request = function(request) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('request', Thrift.MessageType.CALL, this.seqid());
  var args = new sir.wellington.alchemy.thrift.generated.SampleService_request_args();
  args.request = request;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

sir.wellington.alchemy.thrift.generated.SampleServiceClient.prototype.recv_request = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new sir.wellington.alchemy.thrift.generated.SampleService_request_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.success) {
    return callback(null, result.success);
  }
  return callback('request failed: unknown result');
};
sir.wellington.alchemy.thrift.generated.SampleServiceProcessor = exports.Processor = function(handler) {
  this._handler = handler
}
sir.wellington.alchemy.thrift.generated.SampleServiceProcessor.prototype.process = function(input, output) {
  var r = input.readMessageBegin();
  if (this['process_' + r.fname]) {
    return this['process_' + r.fname].call(this, r.rseqid, input, output);
  } else {
    input.skip(Thrift.Type.STRUCT);
    input.readMessageEnd();
    var x = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN_METHOD, 'Unknown function ' + r.fname);
    output.writeMessageBegin(r.fname, Thrift.MessageType.EXCEPTION, r.rseqid);
    x.write(output);
    output.writeMessageEnd();
    output.flush();
  }
}

sir.wellington.alchemy.thrift.generated.SampleServiceProcessor.prototype.process_request = function(seqid, input, output) {
  var args = new sir.wellington.alchemy.thrift.generated.SampleService_request_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.request.length === 1) {
    Q.fcall(this._handler.request, args.request)
      .then(function(result) {
        var result = new sir.wellington.alchemy.thrift.generated.SampleService_request_result({success: result});
        output.writeMessageBegin("request", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        var result = new sir.wellington.alchemy.thrift.generated.SampleService_request_result(err);
        output.writeMessageBegin("request", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.request(args.request,  function (err, result) {
      var result = new sir.wellington.alchemy.thrift.generated.SampleService_request_result((err != null ? err : {success: result}));
      output.writeMessageBegin("request", Thrift.MessageType.REPLY, seqid);
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

