#include "NumberFieldDescriptorContainer.h"
#include <sstream>

using namespace std;
using namespace google;
using namespace protobuf;

QWidget * NumberFieldDescriptorContainer::getWidget(QWidget * parent)
{
    stringstream ss;
    if(m_textField == NULL)
    {
        m_textField = new QLineEdit(parent);

        if (m_type == FieldDescriptor::CPPTYPE_DOUBLE)
        {
            ss << (double)(long) m_value;
            m_textField->setText(QString::fromStdString(ss.str()));
        }
        else if (m_type == FieldDescriptor::CPPTYPE_FLOAT)
        {
            ss << (float)(int) m_value;
            m_textField->setText(QString::fromStdString(ss.str()));
        }
        else if (m_type == FieldDescriptor::CPPTYPE_INT32)
        {
            m_textField->setText(QString((int)m_value));
        }
        else if (m_type == FieldDescriptor::CPPTYPE_INT64)
        {
            ss << (long)m_value ;
            m_textField->setText(QString::fromStdString(ss.str()));
        }
        else if (m_type == FieldDescriptor::CPPTYPE_UINT32)
        {
            m_textField->setText(QString((unsigned int)m_value));
        }
        else if (m_type == FieldDescriptor::CPPTYPE_UINT64)
        {
            ss << (unsigned long)m_value ;
            m_textField->setText(QString::fromStdString(ss.str()));
        }
    }
    else if (m_textField->parentWidget() != parent)
    {
        m_textField->setParent(parent);
    }

    return m_textField;
}

QWidget * NumberFieldDescriptorContainer::getParent()
{
    if(m_textField != NULL)
    {
       return m_textField->parentWidget();
    }

    return NULL;
}

Object * NumberFieldDescriptorContainer::getValue()
{
    stringstream ss;
    if( m_value != NULL)
    {
        if (m_type == FieldDescriptor::CPPTYPE_DOUBLE)
        {
            ss << (double)(long) m_value;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_FLOAT)
        {
            ss << (float)(int) m_value;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_INT32)
        {
            ss << (int) m_value;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_INT64)
        {
            ss << (long )m_value ;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_UINT32)
        {
            ss << (unsigned int )m_value;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_UINT64)
        {
            ss << (unsigned long) m_value ;
        }
        return (Object *) ss.str().c_str();
    }
    else if (m_defaultValue != NULL)
    {
        if (m_type == FieldDescriptor::CPPTYPE_DOUBLE)
        {
            ss << (double)(long) m_defaultValue;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_FLOAT)
        {
            ss << (float)(int) m_defaultValue;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_INT32)
        {
            ss << (int) m_defaultValue;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_INT64)
        {
            ss << (long) m_defaultValue;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_UINT32)
        {
            ss << (unsigned int) m_defaultValue;
        }
        else if (m_type == FieldDescriptor::CPPTYPE_UINT64)
        {
            ss << (unsigned long) m_defaultValue;
        }
        return (Object *) ss.str().c_str();
    }
    return (Object *) "";
}

void NumberFieldDescriptorContainer::setValue(Object * value)
{
   this->m_value = value;
}

QString NumberFieldDescriptorContainer::toString()
{
    string str = "NumberField name = " + this->m_name;
    return QString(str.c_str());
}

bool NumberFieldDescriptorContainer::buildMsg(Message *msg)
{
    if(m_field->is_optional() && m_textField->text().compare("") == 0)
    {
        return false;
    }
    if (m_type == FieldDescriptor::CPPTYPE_DOUBLE)
    {
        if(m_field->is_repeated())
        {
             msg->GetReflection()->AddDouble(msg, m_field, m_textField->text().toDouble());
        }
        else
        {
            msg->GetReflection()->SetDouble(msg, m_field, m_textField->text().toDouble());
        }
    }
    else if (m_type == FieldDescriptor::CPPTYPE_FLOAT)
    {
        if(m_field->is_repeated())
        {
             msg->GetReflection()->AddFloat(msg, m_field, m_textField->text().toFloat());
        }
        else
        {
            msg->GetReflection()->SetFloat(msg, m_field, m_textField->text().toFloat());
        }
    }
    else if (m_type == FieldDescriptor::CPPTYPE_INT32)
    {
        if(m_field->is_repeated())
        {
             msg->GetReflection()->AddInt32(msg, m_field, m_textField->text().toInt());
        }
        else
        {
            msg->GetReflection()->SetInt32(msg, m_field, m_textField->text().toInt());
        }
    }
    else if (m_type == FieldDescriptor::CPPTYPE_INT64)
    {
        if(m_field->is_repeated())
        {
             msg->GetReflection()->AddInt64(msg, m_field, m_textField->text().toLong());
        }
        else
        {
            msg->GetReflection()->SetInt64(msg, m_field, m_textField->text().toLong());
        }
    }
    else if (m_type == FieldDescriptor::CPPTYPE_UINT32)
    {
        if(m_field->is_repeated())
        {
             msg->GetReflection()->AddUInt32(msg, m_field, m_textField->text().toUInt());
        }
        else
        {
            msg->GetReflection()->SetUInt32(msg, m_field, m_textField->text().toUInt());
        }
    }
    else if (m_type == FieldDescriptor::CPPTYPE_UINT64)
    {
        if(m_field->is_repeated())
        {
             msg->GetReflection()->AddUInt64(msg, m_field, m_textField->text().toULong());
        }
        else
        {
            msg->GetReflection()->SetUInt64(msg, m_field, m_textField->text().toULong());
        }
    }
    return true;
}
