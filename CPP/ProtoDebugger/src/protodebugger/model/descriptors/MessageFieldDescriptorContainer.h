#ifndef MESSAGEFIELDDESCRIPTORCONTAINER_H
#define MESSAGEFIELDDESCRIPTORCONTAINER_H

#include <QtGui/QWidget>
#include <QString>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/message.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/dynamic_message.h>
#include "FieldDescriptorContainer.h"

//Forward Declarations
template<class Object> class FieldDescriptorContainer;
class MessageFieldDescriptorContainer : public FieldDescriptorContainer<class Object>
{
    private:
        QWidget * m_msgParent;
        std::vector<FieldDescriptorContainer<class Object> * > m_subMembers;
        void parseMessage();

    public:
        MessageFieldDescriptorContainer(google::protobuf::FieldDescriptor * field):FieldDescriptorContainer<Object>(field)
        {
            parseMessage();
        }
        ~MessageFieldDescriptorContainer()
        {
            delete m_field;
            delete &m_value;
            delete &m_defaultValue;
        }

       void addMember(FieldDescriptorContainer<class Object> * member, int index);
       std::vector<FieldDescriptorContainer<class Object> * > getMembers();
       void setParent(QWidget * parent);
       virtual QWidget * getWidget(QWidget * parent = 0);
       virtual QWidget * getParent();
       virtual Object * getValue();
       virtual void setValue(Object * value);
       virtual QString toString();
       virtual bool buildMsg(google::protobuf::Message * msg);
};

#endif // MESSAGEFIELDDESCRIPTORCONTAINER_H
