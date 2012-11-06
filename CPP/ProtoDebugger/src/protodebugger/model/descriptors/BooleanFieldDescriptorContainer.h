#ifndef BOOLEANFIELDDESCRIPTORCONTAINER_H
#define BOOLEANFIELDDESCRIPTORCONTAINER_H

#include <QtGui/QWidget>
#include <QtGui/QCheckBox>
#include <QString>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/dynamic_message.h>
#include <google/protobuf/message.h>
#include "FieldDescriptorContainer.h"

//Forward Declarations
template<class Object> class FieldDescriptorContainer;

class BooleanFieldDescriptorContainer : public FieldDescriptorContainer<class Object>
{
    private:
        QCheckBox * m_check;

    public:
        BooleanFieldDescriptorContainer(google::protobuf::FieldDescriptor * field):FieldDescriptorContainer<Object>(field){}
       ~BooleanFieldDescriptorContainer()
        {
            delete m_field;
            delete &m_value;
            delete &m_defaultValue;
        }

        virtual QWidget * getWidget(QWidget * parent = 0);
        virtual QWidget * getParent();
        virtual Object * getValue();
        virtual void setValue(Object * value);
        virtual QString toString();
        virtual bool buildMsg(google::protobuf::Message * msg);
};

#endif // BOOLEANFIELDDESCRIPTORCONTAINER_H
