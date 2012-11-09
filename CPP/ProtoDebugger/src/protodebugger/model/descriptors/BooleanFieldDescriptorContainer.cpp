#include "BooleanFieldDescriptorContainer.h"
#include <google/protobuf/message.h>
#include <sstream>

using namespace std;
using namespace google;
using namespace protobuf;

QWidget * BooleanFieldDescriptorContainer::getWidget(QWidget * parent)
{
    if(m_check == NULL)
    {
        m_check = new QCheckBox(parent);

        if (m_type == FieldDescriptor::CPPTYPE_BOOL)
        {
            m_check->setChecked((bool)m_value);
        }
    }
    else if (m_check->parentWidget() != parent)
    {
        m_check->setParent(parent);
    }

    return m_check;
}

QWidget * BooleanFieldDescriptorContainer::getParent()
{
    if(m_check != NULL)
    {
       return m_check->parentWidget();
    }

    return NULL;
}

Object * BooleanFieldDescriptorContainer::getValue()
{
    stringstream ss;
    if(m_value != NULL)
    {
        if (m_type == FieldDescriptor::CPPTYPE_BOOL)
        {
            ss << (bool) m_value;
            return (Object *) ss.str().c_str();
        }
     }
    else if (m_defaultValue != NULL)
    {

    }

    return (Object *) "";
}

void BooleanFieldDescriptorContainer::setValue(Object * value)
{
   this->m_value = value;
}

QString BooleanFieldDescriptorContainer::toString()
{
    string str = "BooleanField name = " + this->m_name;
    return QString(str.c_str());
}

//Note: Using it passes in a mutable message
bool BooleanFieldDescriptorContainer::buildMsg(Message * msg)
{
    if(m_field->is_repeated())
    {
        msg->GetReflection()->AddBool(msg, m_field, m_check->isChecked());
    }
    else
    {
        msg->GetReflection()->SetBool(msg, m_field, m_check->isChecked());
    }
    return true;
}
