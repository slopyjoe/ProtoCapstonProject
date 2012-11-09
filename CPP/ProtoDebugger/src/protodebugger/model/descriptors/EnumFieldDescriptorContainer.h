#ifndef ENUMFIELDDESCRIPTORCONTAINER_H
#define ENUMFIELDDESCRIPTORCONTAINER_H

#include <QtGui/QWidget>
#include <QtGui/QComboBox>
#include <QString>
#include <QStringList>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/message.h>
#include "FieldDescriptorContainer.h"

//Forward Declarations
template<class Object> class FieldDescriptorContainer;

class EnumFieldDescriptorContainer : public FieldDescriptorContainer<class Object>
{
    private:
        QComboBox * m_comboBox;

    public:
        EnumFieldDescriptorContainer(google::protobuf::FieldDescriptor * field):FieldDescriptorContainer<Object>(field){}
        ~EnumFieldDescriptorContainer()
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

        QStringList getValues();
};

#endif // ENUMFIELDDESCRIPTORCONTAINER_H
