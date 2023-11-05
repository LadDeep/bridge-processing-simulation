## Bridge Frame Processing

This project involves simulating frame processing in a bridge network. The system comprises two programs: `FrameGenerator` and `BridgeProcessor`.

### Frame Generator

The `FrameGenerator` class is designed to randomly generate frames with source and destination MAC addresses, as well as arrival port numbers. The class utilizes a text file (`BridgeFDB.txt`) containing the forwarding database (FDB) of a bridge with four ports. The generated frames are stored in an output file specified by the user. The program ensures that the MAC addresses and port numbers adhere to the information in the FDB.

### Frame Processing

The `BridgeProcessor` class manages the processing of frames based on the information in the FDB. It reads two text files (`BridgeFDB.txt` and `RandomFrames.txt`), processes each random frame, and determines whether to forward, discard, or broadcast the frame. The resulting output is stored in another text file (`BridgeOutput.txt`). The class also handles updating the FDB when necessary.

#### Usage

To use the `FrameGenerator`, instantiate the class with the number of frames to generate, the output file name, and the maximum number of ports. Then, call the `generateRandomFrames` method.

```
// Code generates random frames in RandomFrames.txt. Change name as needed
FrameGenerator frameGenerator = new FrameGenerator(100, "RandomFrames.txt", 4);
frameGenerator.generateRandomFrames();
```

To process the generated frames, create an instance of BridgeProcessor and call the processFrame method for each frame. To simulate all the frame processing, logic is required to process all the frames in the `RandomFrames.txt.`. Sample is provided in `Application.java`

```
// processing for each frame
BridgeProcessor bridgeProcessor = new BridgeProcessor();
bridgeProcessor.processFrame(sourceMAC, destMAC, arrivalPort);
```