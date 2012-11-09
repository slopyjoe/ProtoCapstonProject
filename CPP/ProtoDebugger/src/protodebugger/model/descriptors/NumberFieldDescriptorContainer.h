#ifndef NUMBERFIELDDESCRIPTORCONTAINER_H
#define NUMBERFIELDDESCRIPTORCONTAINER_H

#include <QtGui/QWidget>
#include <QtGui/QLineEdit>
#include <QString>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/message.h>
#include "FieldDescriptorContainer.h"

//Forward Declarations
template<class Object> class FieldDescriptorContainer;

class NumberFieldDescriptorContainer : public FieldDescriptorContainer<class Object>
{
    private:
        QLineEdit * m_textField;

    public:
        NumberFieldDescriptorContainer(google::protobuf::FieldDescriptor * field):FieldDescriptorContainer<Object>(field){}
        ~NumberFieldDescriptorContainer()
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

#endif // NUMBERFIELDDESCRIPTORCONTAINER_H
