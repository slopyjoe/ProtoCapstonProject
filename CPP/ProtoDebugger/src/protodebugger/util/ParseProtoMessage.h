#ifndef PARSEPROTOMESSAGE_H
#define PARSEPROTOMESSAGE_H

class ParseProtoMessage
{
    private:
        ParseProtoMessage();
        ParseProtoMessage(ParseProtoMessage const&);              // Don't Implement.
        void operator=(ParseProtoMessage const&); // Don't implement

    public:
        static ParseProtoMessage& getInstance()
        {
            static ParseProtoMessage instance;
            return instance;
        }
};

#endif // PARSEPROTOMESSAGE_H
