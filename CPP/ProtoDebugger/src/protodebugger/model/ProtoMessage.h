#ifndef PROTOMESSAGE_H
#define PROTOMESSAGE_H

//protobuf
#include <google/protobuf/descriptor.h>
#include <google/protobuf/descriptor.pb.h>
#include <google/protobuf/dynamic_message.h>

//Qt
#include <QWidget>

#include "FieldDescriptorContainer.h"

using namespace std;
using namespace google;
using namespace protobuf;

template <class Object> class ProtoMessage
{
    private:
        DynamicMessageFactory m_genMsg;
        list<FieldDescriptorContainer> m_contents;
        list<FieldDescriptorContainer> m_repeatedFields;
        list<FieldDescriptorContainer> m_addFields;

    public:
        ProtoMessage(DynamicMessageFactory genMsg, list<FieldDescriptorContainer> contents,
        list<FieldDescriptorContainer> repeatedField, list<FieldDescriptorContainer> addFields)
        {
            m_genMsg = genMsg;
            m_contents = contents;
            m_repeatedFields = repeatedFields;
        }

        Message::Message getGenMsg()
        {
            return m_genMsg;
        }

        void setGenMsg(Message::Message genMsg)
        {
            m_genMsg = genMsg;
        }

        list<FieldDescriptorContainer> getContents()
        {
            return m_contents;
        }

        void setContents(list<FieldDescriptorContainer> contents)
        {
            m_conents = contents;
        }


        list<FieldDescriptorContainer> getRepeatedFields()
        {
            return m_repeatedFields;
        }

        void setRepeatedFields(list<FieldDescriptorContainer> repeatedFields)
        {
           m_repeatedFields = repeatedFields;
        }

        void addAddField(FieldDescriptorContainer add)
        {
            if(addFields == NULL)
            {
                m_addFields = new list<FieldDescriptorContainer>();
            }
            m_addFields.insert(add);
        }

        void removeAddField(FieldDescriptorContainer remove)
        {
            if(addFields == NULL)
            {
                return;
            }
            m_addFields.remove(remove);
        }

        list<FieldDescriptorContainer> getAddFields()
        {
             return m_addFields;
        }
};
#endif // PROTOMESSAGE_H
