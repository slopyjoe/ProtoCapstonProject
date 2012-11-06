#include "MessageFieldDescriptorContainer.h"
#include <sstream>
#include <iostream>

using namespace std;
using namespace google;
using namespace protobuf;

void MessageFieldDescriptorContainer::parseMessage()
{

}

void MessageFieldDescriptorContainer::addMember(FieldDescriptorContainer<Object> * member, int index)
{
    m_subMembers.insert(m_subMembers.begin() + index, member);
}

std::vector<FieldDescriptorContainer<class Object> *>  MessageFieldDescriptorContainer::getMembers()
{
    return m_subMembers;
}

QWidget * MessageFieldDescriptorContainer::getWidget(QWidget * parent)
{
    return NULL;
}

QWidget * MessageFieldDescriptorContainer::getParent()
{
    return m_msgParent;
}
void MessageFieldDescriptorContainer::setParent(QWidget * parent)
{
    m_msgParent = parent;
}

Object * MessageFieldDescriptorContainer::getValue()
{
    return NULL;
}

void MessageFieldDescriptorContainer::setValue(Object * value){}

QString MessageFieldDescriptorContainer::toString()
{
    stringstream ss;

    ss << "MessageField name = " + this->m_name + "/n";

    vector<FieldDescriptorContainer<class Object> *>::iterator iter;
    for (iter = m_subMembers.begin(); iter != m_subMembers.end(); ++iter)
    {
        ss << "\t" + (*iter)->toString().toStdString() + "\n";
    }
    return QString(ss.str().c_str());
}

bool MessageFieldDescriptorContainer::buildMsg(Message * msg)
{
    //Not sure if this is correct, but send an innerMsg to build this msg
    Message * innerMsg;

    vector<FieldDescriptorContainer<class Object> *>::iterator iter;
    for (iter = m_subMembers.begin(); iter != m_subMembers.end(); ++iter)
    {
        cout << "Building " + (*iter)->toString().toStdString();
        (*iter)->buildMsg(innerMsg);
    }
    if( m_field->is_repeated() )
    {
       msg->GetReflection()->AddMessage(innerMsg, m_field);
    }
    else
    {
        msg->GetReflection()->AddMessage(innerMsg, m_field);
    }
    return true;
}
